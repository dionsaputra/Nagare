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
import com.nagare.model.Lokasi;
import com.nagare.util.DataUtil;

public class LaporanKuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView name, description;
    private Lokasi laporan;
    private Toast toast;

    public LaporanKuViewHolder(View view) {
        super(view);
        initComponent(view);
        view.setOnClickListener(this);
    }

    private void initComponent(View view) {
        name        = view.findViewById(R.id.tv_lokasi_ku_name);
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

    public void bind(Lokasi laporan) {
        this.laporan = laporan;
        name.setText(laporan.getName());
        description.setText(laporan.getDescription());
    }

    public void removeEntityFromFirebase() {
        DataUtil.dbLapor.child(laporan.getKey()).removeValue();
    }

    public void editHolderEntity(View v) {
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        View editView = inflater.inflate(R.layout.crud_lokasi,null);
        final EditText laporanName = editView.findViewById(R.id.lokasi_name_et);
        laporanName.setText(laporan.getName());

        final EditText laporanDescription = editView.findViewById(R.id.lokasi_description_et);
        laporanDescription.setText(laporan.getDescription());

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
        builder.setTitle("Edit")
                .setView(editView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        laporan.setName(laporanName.getText().toString());
                        laporan.setDescription(laporanDescription.getText().toString());
                        editEntityInFirebase();

                    }
                })
                .setNegativeButton("Cancel",null);
        builder.create().show();
    }

    private void editEntityInFirebase() {
        DataUtil.dbLapor.child(laporan.getKey()).setValue(laporan);
    }

    public void showAToast (View v,String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
