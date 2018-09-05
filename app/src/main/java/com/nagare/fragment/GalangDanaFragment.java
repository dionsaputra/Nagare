package com.nagare.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import java.util.ArrayList;

public class GalangDanaFragment extends BaseMainFragment implements GalangDanaAdapter.GalangDanaClickHandler{

    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;

    private GalangDanaAdapter.GalangDanaClickHandler clickHandler = this;

    public GalangDanaFragment() {
        super();
        layoutResId = R.layout.fragment_galang_dana;
    }

    @Override
    protected void initComponent() {
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        galangDanaRecyclerView = rootView.findViewById(R.id.rv_galang_dana);
        galangDanaAdapter = new GalangDanaAdapter(this);

        final ArrayList<GalangDana> newGalangDana = new ArrayList<>();
        DataUtil.getInstance().dbGalangDana.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    GalangDana gdItem = item.getValue(GalangDana.class);
                    newGalangDana.add(gdItem);
                    Log.d("ITEM", gdItem.title);
                }
                DataUtil.getInstance().setGalangDanas(newGalangDana);
                galangDanaAdapter.galangDanas = newGalangDana;
                galangDanaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void setupComponent() {
        galangDanaRecyclerView.setLayoutManager(layoutManager);
        galangDanaRecyclerView.setHasFixedSize(true);
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

    @Override
    public void onClick(int pos) {

    }
}
