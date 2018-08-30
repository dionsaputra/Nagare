package com.nagare.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.base.BaseMainFragment;

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
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

    @Override
    public void onClick(int pos) {
        Toast.makeText(getContext(), "" + pos, Toast.LENGTH_SHORT).show();
    }
}
