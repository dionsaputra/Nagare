package com.nagare.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.model.User;
import com.nagare.util.DataUtil;

import java.util.Date;

public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView descImage;
    private TextView title, owner, amount;
    private ProgressBar progressBar;
    private GalangDana galangDana;

    public GalangDanaViewHolder(View itemView) {
        super(itemView);
        initComponent(itemView);
        itemView.setOnClickListener(this);
    }

    private void initComponent(View view) {
        title       = view.findViewById(R.id.tv_galang_dana_title);
        owner       = view.findViewById(R.id.tv_galang_dana_owner);
        amount      = view.findViewById(R.id.tv_galang_dana_amount);
        progressBar = view.findViewById(R.id.pb_galang_dana_progress);
    }

    @Override
    public void onClick(View v) {

    }

    public void bind(GalangDana galangDana) {
        this.galangDana = galangDana;
        title.setText(galangDana.getTitle());
        amount.setText(String.valueOf(galangDana.getTargetDana()));

        Query query = DataUtil.dbUser.child(galangDana.getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                owner.setText(dataSnapshot.getValue(User.class).getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
