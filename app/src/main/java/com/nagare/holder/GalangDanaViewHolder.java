package com.nagare.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.util.ViewUtil;

public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView descImage;
    private TextView title, owner, amount;
    private ProgressBar progress;
    private GalangDana galangDana;

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

    public void bind(GalangDana galangDana) {
        descImage.setImageResource(R.drawable.itb);
        title.setText(galangDana.title);
        owner.setText(galangDana.owner);
        amount.setText(galangDana.currentDana + "/" + galangDana.targetDana);
        progress.setProgress((int) (galangDana.currentDana * 100 / galangDana.targetDana));
    }
}
