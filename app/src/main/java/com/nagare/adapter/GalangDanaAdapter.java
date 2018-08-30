package com.nagare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nagare.R;
import com.nagare.util.DataUtil;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaAdapter.GalangDanaViewHolder> {
    private int pos;
    private final GalangDanaClickHandler clickHandler;

    public interface GalangDanaClickHandler {
        void onClick(int pos);
    }

    public GalangDanaAdapter(GalangDanaClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public GalangDanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_galang_dana, parent, false);
        return new GalangDanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalangDanaViewHolder holder, int position) {
        pos = position;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public GalangDanaViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick(getAdapterPosition());
        }
    }
}
