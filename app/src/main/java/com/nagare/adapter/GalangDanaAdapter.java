package com.nagare.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.DetailGalangDanaActivity;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class GalangDanaAdapter extends RecyclerView.Adapter<GalangDanaAdapter.GalangDanaViewHolder> {
    private final GalangDanaClickHandler clickHandler;
    private ArrayList<GalangDana> galangDanas = DataUtil.getInstance().generateGalangDana();

    public interface GalangDanaClickHandler {
        void onClick(int pos);
    }

    public GalangDanaAdapter(GalangDanaClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView descImage;
        private TextView title, owner, address;

        public GalangDanaViewHolder(View view) {
            super(view);
            initComponent(view);
            view.setOnClickListener(this);
        }

        private void initComponent(View view) {
            descImage   = view.findViewById(R.id.iv_selected_galang_dana);
            title       = view.findViewById(R.id.tv_galang_dana_title);
            owner       = view.findViewById(R.id.tv_galang_dana_owner);
            address     = view.findViewById(R.id.tv_galang_dana_address);
        }

        @Override
        public void onClick(View v) {
            ViewUtil.startNewActivity(v.getContext(), DetailGalangDanaActivity.class, descImage, R.string.tn_selected_galang_dana);
        }

        public void bind(int position) {
            descImage.setImageResource(R.drawable.itb);
            title.setText(galangDanas.get(position).title);
            owner.setText(galangDanas.get(position).owner);
            address.setText(DataUtil.getInstance().randomStr(10));
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
