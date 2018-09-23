package com.nagare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagare.R;
import com.nagare.holder.AcaraKuViewHolder;
import com.nagare.holder.GalangDanaViewHolder;
import com.nagare.model.GalangDana;

import java.util.ArrayList;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaViewHolder>{
    ArrayList<GalangDana> galangDanas;

    public GalangDanaAdapter(ArrayList<GalangDana> galangDanas) {
        this.galangDanas = galangDanas;
    }

    @NonNull @Override
    public GalangDanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_galang_dana, parent, false);
        return new GalangDanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalangDanaViewHolder holder, int position) {
        holder.bind(galangDanas.get(position));
    }

    @Override
    public int getItemCount() {
        return galangDanas.size();
    }
}
