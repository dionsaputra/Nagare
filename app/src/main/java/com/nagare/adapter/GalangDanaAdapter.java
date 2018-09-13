package com.nagare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import java.util.ArrayList;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaAdapter.GalangDanaViewHolder> {
    private final GalangDanaClickHandler clickHandler;
    public ArrayList<GalangDana> galangDanas;

    public interface GalangDanaClickHandler {
        void onClick(int pos);
    }

    public GalangDanaAdapter(GalangDanaClickHandler clickHandler, ArrayList<GalangDana> galangDanas) {
        this.clickHandler = clickHandler;
        this.galangDanas = galangDanas;
    }

    public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView descImage;
        private TextView title, owner, amount;
        private ProgressBar progress;

        public GalangDanaViewHolder(View view) {
            super(view);
            initComponent(view);
            view.setOnClickListener(this);
        }

        private void initComponent(View view) {
            descImage   = view.findViewById(R.id.iv_selected_galang_dana);
            title       = view.findViewById(R.id.tv_galang_dana_title);
            owner       = view.findViewById(R.id.tv_galang_dana_owner);
            amount      = view.findViewById(R.id.tv_galang_dana_amount);
            progress    = view.findViewById(R.id.pb_galang_dana_progress);
        }

        @Override
        public void onClick(View v) {
            ViewUtil.startNewActivity(v.getContext(), DetailGalangDanaActivity.class, descImage, R.string.tn_selected_galang_dana);
        }

        public void bind(int position) {
            descImage.setImageResource(R.drawable.itb);
            title.setText(galangDanas.get(position).title);
            owner.setText(galangDanas.get(position).owner);
            amount.setText(galangDanas.get(position).currentDana + "/" + galangDanas.get(position).targetDana);
            progress.setProgress((int) (galangDanas.get(position).currentDana * 100 / galangDanas.get(position).targetDana));
        }
    }

    @NonNull
    @Override
    public GalangDanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_galang_dana, parent, false);

        return new GalangDanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalangDanaViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return galangDanas.size();
    }
}
