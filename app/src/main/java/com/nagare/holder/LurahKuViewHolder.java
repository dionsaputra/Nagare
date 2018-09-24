package com.nagare.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.R;
import com.nagare.model.Kalender;
import com.nagare.util.DataUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LurahKuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private LinearLayout background;
    private TextView title, description, date;
    private Kalender kalender;
    private Toast toast;

    public LurahKuViewHolder(View view) {
        super(view);
        initComponent(view);
        view.setOnClickListener(this);
    }

    private void initComponent(View view) {
        title       = view.findViewById(R.id.tv_calendar_ku_title);
        description = view.findViewById(R.id.tv_calendar_ku_description);
        date        = view.findViewById(R.id.tv_calendar_ku_date);
        background  = view.findViewById(R.id.ll_fasilitas_ku_list_item);
        if(kalender != null){
            if(kalender.status == 1){
                background.setBackgroundColor(Color.parseColor("#2ecc71"));
            } else if (kalender.status == 2){
                background.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
        }

    }

    @Override
    public void onClick(View v) {
        showHolderOptions(v);
    }

    public void showHolderOptions(final View v) {
        String[] item;
        if(DataUtil.USER_LURAH){
            item = new String[]{"Edit", "Delete", "Accept", "Reject"};
        } else {
            item = new String[]{"Edit", "Delete"};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Options")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (DataUtil.USER_LURAH){
                            switch (which) {
                                case 0 : editHolderEntity(v); break;
                                case 1 : deleteHolderEntity(v); break;
                                case 2 : acceptHolderEntity(v); break;
                                case 3 : rejectHolderEntity(v); break;
                            }
                        } else {
                            switch (which) {
                                case 0 : editHolderEntity(v); break;
                                case 1 : deleteHolderEntity(v); break;
                            }
                        }

                    }
                });
        builder.create().show();
    }
    public void acceptHolderEntity(View v){
        kalender.setStatus(1);
        editEntityInFirebase();
    }

    public void rejectHolderEntity(View v){
        kalender.setStatus(2);
        editEntityInFirebase();
    }


    public void deleteHolderEntity(View v) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
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

    public void bind(Kalender kalender) {
        this.kalender = kalender;
        title.setText(kalender.getTitle());
        description.setText(kalender.getDescription());
        date.setText(DateFormat.format("MM/dd/yyyy", new Date(kalender.getDate())).toString());
    }

    public void removeEntityFromFirebase() {
        DataUtil.dbTemuLurah.child(kalender.getKey()).removeValue();
    }

    public void editHolderEntity(View v) {
        final Context context = v.getContext();
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        View editView = inflater.inflate(R.layout.dialog_acara,null);
        final EditText etName = editView.findViewById(R.id.et_nama_acara);
        final EditText etDesc = editView.findViewById(R.id.et_deskripsi_acara);
        final EditText etTanggal = editView.findViewById(R.id.et_date_picker);
        etName.setText(kalender.getTitle());
        etDesc.setText(kalender.getDescription());
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                // Random initial selected date
                int mYear = 2010, mMonth = 1, mDay = 2;
                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etTanggal.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }

        });

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
        builder.setTitle("Edit")
                .setView(editView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kalender.setTitle(etName.getText().toString());
                        kalender.setDescription(etDesc.getText().toString());
                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("y-M-d");
                        String tDate = etTanggal.getText().toString();
                        long mDate = 0;
                        try {
                            mDate = mSimpleDateFormat.parse(tDate).getTime();
                        } catch (ParseException e) {}
                        kalender.setDate(mDate);
                        editEntityInFirebase();

                    }
                })
                .setNegativeButton("Cancel",null);
        builder.create().show();
    }

    private void editEntityInFirebase() {
        if(kalender != null){
            if(kalender.status == 1){
                background.setBackgroundColor(Color.parseColor("#2ecc71"));
            } else if (kalender.status == 2){
                background.setBackgroundColor(Color.parseColor("#e74c3c"));
            }
        }
        DataUtil.dbTemuLurah.child(kalender.getKey()).setValue(kalender);
    }

    public void showAToast (View v,String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
