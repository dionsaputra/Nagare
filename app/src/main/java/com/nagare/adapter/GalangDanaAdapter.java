package com.nagare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nagare.R;
import com.nagare.util.DataUtil;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaAdapter.GalangDanaViewHolder> {

    
    @NonNull
    @Override
    public GalangDanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_galang_dana, parent, false);
        return new GalangDanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalangDanaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GalangDanaViewHolder extends RecyclerView.ViewHolder{
        public GalangDanaViewHolder(View view) {
            super(view);
        }
    }
}
