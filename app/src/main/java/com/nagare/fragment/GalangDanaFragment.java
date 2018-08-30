package com.nagare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;

public class GalangDanaFragment extends Fragment {

    private View rootView;
    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_galang_dana, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
    }

    private void initComponent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        galangDanaRecyclerView = rootView.findViewById(R.id.rv_galang_dana);
        galangDanaRecyclerView.setLayoutManager(layoutManager);
        galangDanaRecyclerView.setHasFixedSize(true);
        galangDanaAdapter = new GalangDanaAdapter();
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

}
