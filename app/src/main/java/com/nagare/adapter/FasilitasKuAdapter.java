package com.nagare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagare.R;
import com.nagare.holder.FasilitasKuViewHolder;
import com.nagare.model.Fasilitas;

import java.util.ArrayList;

public class FasilitasKuAdapter extends RecyclerView.Adapter<FasilitasKuViewHolder> {
    public ArrayList<Fasilitas> fasilitasKus;

    public FasilitasKuAdapter(ArrayList<Fasilitas> fasilitasKus) {
        this.fasilitasKus = fasilitasKus;
    }

    @NonNull
    @Override
    public FasilitasKuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fasilitas_ku, parent, false);
        return new FasilitasKuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasKuViewHolder holder, int position) {
        holder.bind(fasilitasKus.get(position));
    }

    @Override
    public int getItemCount() {
        return fasilitasKus.size();
    }
}
