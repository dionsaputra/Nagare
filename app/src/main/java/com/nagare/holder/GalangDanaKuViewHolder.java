package com.nagare.holder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GalangDanaKuViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private ImageView imageView;
    private TextView title, owner, amount;
    private ProgressBar progressBar;
    private GalangDana galangDana;

    public GalangDanaKuViewHolder(View itemView) {
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
        showHolderOptions(v);
    }

    public void showHolderOptions(final View v) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        builder.setTitle("Options")
                .setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 : editHolderEntity(v); break;
                            case 1 : deleteHolderEntity(v); break;
                        }
                    }
                });
        builder.create().show();
    }

    public void deleteHolderEntity(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Delete")
                .setMessage("Are you sure to delete this items ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeEntityFromFirebase();
                    }
                })
                .setNegativeButton("Cancel",null);
        builder.create().show();
    }

    public void removeEntityFromFirebase() {
        DataUtil.dbGalangDana.child(galangDana.getKey()).removeValue();
    }

    public void editHolderEntity(View v) {
        final Context context = v.getContext();
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();

        View inflator = ((AppCompatActivity) v.getContext()).getLayoutInflater().inflate(R.layout.dialog_galang_dana, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        final EditText gdName = inflator.findViewById(R.id.et_nama_galang_dana);
        final EditText gdDesc = inflator.findViewById(R.id.et_deskripsi_galang_dana);
        final EditText gdNominal = inflator.findViewById(R.id.et_target_galang_dana);
        final EditText gdDeadline = inflator.findViewById(R.id.et_deadline_galang_dana);

        gdDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v, gdDeadline);
            }
        });

        gdName.setText(galangDana.getTitle());
        gdDesc.setText(galangDana.getDescription());
        gdNominal.setText(String.valueOf(galangDana.getTargetDana()));

        builder.setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = gdName.getText().toString();
                        String desc = gdDesc.getText().toString();
                        Long nominal = Long.parseLong(gdNominal.getText().toString());

                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("y-M-d");
                        String strDate = gdDeadline.getText().toString();

                        long deadline = 0;
                        try {
                            deadline = mSimpleDateFormat.parse(strDate).getTime();
                        } catch (ParseException e) {}

                        galangDana.setTitle(name);
                        galangDana.setDescription(desc);
                        galangDana.setTargetDana(nominal);
                        galangDana.setLimitWaktu(deadline);

                        DataUtil.dbGalangDana.child(galangDana.getKey()).setValue(galangDana);
                    }
                });
        builder.create().show();
    }

    private void showDatePicker(View view, final EditText deadline) {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        // Random initial selected date
        int mYear = 2010, mMonth = 1, mDay = 2;
        DatePickerDialog dpd = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        deadline.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    private void editEntityInFirebase() {
        DataUtil.dbGalangDana.child(galangDana.getKey()).setValue(galangDana);
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
