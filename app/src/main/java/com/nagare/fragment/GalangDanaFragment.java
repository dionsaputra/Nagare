package com.nagare.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.base.BaseMainFragment;
import com.nagare.util.DataUtil;

public class GalangDanaFragment extends BaseMainFragment implements GalangDanaAdapter.GalangDanaClickHandler{

    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;

    public GalangDanaFragment() {
        super();
        layoutResId = R.layout.fragment_galang_dana;
    }

    @Override
    protected void initComponent() {
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        galangDanaRecyclerView = rootView.findViewById(R.id.rv_galang_dana);
        galangDanaAdapter = new GalangDanaAdapter(this);
    }

    @Override
    protected void setupComponent() {
        galangDanaRecyclerView.setLayoutManager(layoutManager);
        galangDanaRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(int pos) {

    }
}
