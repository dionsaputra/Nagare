package com.nagare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nagare.R;
import com.nagare.holder.AcaraKuViewHolder;
import com.nagare.holder.LurahKuViewHolder;
import com.nagare.model.Kalender;

import java.util.ArrayList;

public class LurahKuAdapter extends RecyclerView.Adapter<LurahKuViewHolder> {
    public ArrayList<Kalender> lurahKus;

    public LurahKuAdapter(ArrayList<Kalender> lurahKus) {
        this.lurahKus = lurahKus;
    }

    @NonNull
    @Override
    public LurahKuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar_ku, parent, false);
        return new LurahKuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LurahKuViewHolder holder, int position) {
        holder.bind(lurahKus.get(position));
    }

    @Override
    public int getItemCount() {
        return lurahKus.size();
    }
}


