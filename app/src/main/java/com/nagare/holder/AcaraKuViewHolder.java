package com.nagare.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.R;
import com.nagare.model.Calendar;
import com.nagare.util.DataUtil;

public class AcaraKuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView title, description, date;
    private Calendar calendar;
    private Toast toast;

    public AcaraKuViewHolder(View view) {
        super(view);
        initComponent(view);
        view.setOnClickListener(this);
    }

    private void initComponent(View view) {
        title        = view.findViewById(R.id.tv_lokasi_ku_name);
        description = view.findViewById(R.id.tv_lokasi_ku_description);
    }

    @Override
    public void onClick(View v) {
        showHolderOptions(v);
    }

    public void showHolderOptions(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
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

    public void bind(Calendar calendar) {
        this.calendar = calendar;
        title.setText(calendar.getTitle());
        description.setText(calendar.getDescription());
    }

    public void removeEntityFromFirebase() {
        DataUtil.dbAcara.child(calendar.getKey()).removeValue();
    }

    public void editHolderEntity(View v) {
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        View editView = inflater.inflate(R.layout.dialog_acara,null);
        final EditText acaraTitle = editView.findViewById(R.id.et_nama_acara);
        acaraTitle.setText(calendar.getTitle());

        final EditText acaraDescription = editView.findViewById(R.id.et_deskripsi_acara);
        acaraDescription.setText(calendar.getDescription());

        final EditText acaraDate = editView.findViewById(R.id.et_date_picker);
//
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
//        builder.setTitle("Edit")
//                .setView(editView)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        calendar.setTitle(acaraTitle.getText().toString());
//                        calendar.setDescription(acaraDescription.getText().toString());
//                        calendar.setDate(Long.parseLong(acaraDate.getText().toString()));
//                        editEntityInFirebase();
//
//                    }
//                })
//                .setNegativeButton("Cancel",null);
//        builder.create().show();
    }

    private void editEntityInFirebase() {
        DataUtil.dbAcara.child(calendar.getKey()).setValue(calendar);
    }

    public void showAToast (View v,String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
