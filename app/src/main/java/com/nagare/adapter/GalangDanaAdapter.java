package com.nagare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import java.util.ArrayList;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaAdapter.GalangDanaViewHolder> {
    private final GalangDanaClickHandler clickHandler;
    private ArrayList<GalangDana> galangDanas;

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
//        holder.getGalangDanaDescImage().setTransitionName("tn_galang_dana_" + position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView galangDanaDescImage;

        public GalangDanaViewHolder(View view) {
            super(view);
            galangDanaDescImage = view.findViewById(R.id.iv_selected_galang_dana);
//            ViewUtil.loadImage(view.getContext(), galangDanaDescImage, R.drawable.itb);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ViewUtil.startNewActivity(v.getContext(), DetailGalangDanaActivity.class, galangDanaDescImage, R.string.tn_selected_galang_dana);
        }

        public ImageView getGalangDanaDescImage() {
            return galangDanaDescImage;
        }

        public void setGalangDanaDescImage(ImageView galangDanaDescImage) {
            this.galangDanaDescImage = galangDanaDescImage;
        }
    }
}
