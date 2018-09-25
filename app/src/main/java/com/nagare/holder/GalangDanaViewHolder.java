package com.nagare.holder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.EditKuActivity;
import com.nagare.R;
import com.nagare.model.GalangDana;
import com.nagare.model.Kalender;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class GalangDanaViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener{

    private ImageView imageView;
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
        imageView   = view.findViewById(R.id.iv_selected_galang_dana);
        ViewUtil.loadImage(view.getContext(), imageView, ViewUtil.getRandomPlaceHolder());
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = ((AppCompatActivity) v.getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.detail_galang_dana,null);
        ImageView imageView = view.findViewById(R.id.iv_selected_galang_dana_dialog);

        TextView gdName = view.findViewById(R.id.tv_galang_dana_dialog_name);
        final TextView gdOwner = view.findViewById(R.id.tv_galang_dana_dialog_owner);
        TextView gdDesc = view.findViewById(R.id.tv_galang_dana_description);

        gdName.setText(galangDana.getTitle());

        Query query = DataUtil.dbUser.child(galangDana.getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gdOwner.setText(dataSnapshot.getValue(User.class).getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        gdDesc.setText(galangDana.getDescription());

        ViewUtil.loadImage((AppCompatActivity) v.getContext(), imageView, ViewUtil.getRandomPlaceHolder());

        AlertDialog.Builder builder = new AlertDialog.Builder((AppCompatActivity) v.getContext(), R.style.MyAlertDialogTheme);
        builder.setView(view)
                .setPositiveButton("OK",null);

        builder.create().show();
    }

    public void bind(GalangDana galangDana) {
        this.galangDana = galangDana;
        title.setText(galangDana.getTitle());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
        amount.setText(String.format(formatter.format(galangDana.getTargetDana())));
        progressBar.setMax(galangDana.getTargetDana().intValue());
        progressBar.setProgress(galangDana.getCurrentDana().intValue());

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
