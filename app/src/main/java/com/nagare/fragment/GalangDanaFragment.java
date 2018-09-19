package com.nagare.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalangDanaFragment extends BaseMainFragment implements GalangDanaAdapter.GalangDanaClickHandler{

    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<GalangDana> allGalangDana;
    DatabaseReference dbGalangDana;

    private GalangDanaAdapter.GalangDanaClickHandler clickHandler = this;

    public GalangDanaFragment() {
        super();
        layoutResId = R.layout.fragment_galang_dana;
    }

    @Override
    protected void initComponent() {
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        galangDanaRecyclerView = rootView.findViewById(R.id.rv_galang_dana);

        dbGalangDana = FirebaseDatabase.getInstance().getReference().child("galang-danas");

        Button button = rootView.findViewById(R.id.dummy_galang_dana);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = dbGalangDana.push().getKey();
                GalangDana gd = new GalangDana("" +
                        "Title",
                        "Description",
                        "Owner",
                        1000L,
                        100L,
                        10L);
                dbGalangDana.child(key).setValue(gd);
            }
        });
    }

    @Override
    protected void setupComponent() {
        galangDanaRecyclerView.setLayoutManager(layoutManager);
        galangDanaRecyclerView.setHasFixedSize(true);

        dbGalangDana.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllGalangDana(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeAllGalangDana(DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            for (int i=0; i<allGalangDana.size(); i++) {
                if (allGalangDana.get(i).equals(singleSnapshot.getValue(GalangDana.class))) {
                    allGalangDana.remove(i);
                }
            }
        }
        galangDanaAdapter = new GalangDanaAdapter(this,allGalangDana);
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

    private void getAllGalangDana(DataSnapshot dataSnapshot) {
        allGalangDana = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            allGalangDana.add(ds.getValue(GalangDana.class));
        }
        galangDanaAdapter = new GalangDanaAdapter(clickHandler,allGalangDana);
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }


    @Override
    public void onClick(int pos) {

    }
}
