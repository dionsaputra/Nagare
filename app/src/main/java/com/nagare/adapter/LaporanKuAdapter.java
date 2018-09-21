package com.nagare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagare.R;
import com.nagare.holder.LaporanKuViewHolder;
import com.nagare.model.Lokasi;

import java.util.ArrayList;

public class LaporanKuAdapter extends RecyclerView.Adapter<LaporanKuViewHolder> {
    public ArrayList<Lokasi> laporanKus;

    public LaporanKuAdapter(ArrayList<Lokasi> laporanKus) {
        this.laporanKus = laporanKus;
    }

    @NonNull
    @Override
    public LaporanKuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_lokasi_ku, parent, false);
        return new LaporanKuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanKuViewHolder holder, int position) {
        holder.bind(laporanKus.get(position));
    }

    @Override
    public int getItemCount() {
        return laporanKus.size();
    }
}
