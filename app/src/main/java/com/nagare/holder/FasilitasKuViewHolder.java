package com.nagare.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.model.Fasilitas;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

public class FasilitasKuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView name, description;
    private Fasilitas fasilitas;
    private Toast toast;

    public FasilitasKuViewHolder(View view) {
        super(view);
        initComponent(view);
        view.setOnClickListener(this);
    }

    private void initComponent(View view) {
        name        = view.findViewById(R.id.tv_fasilitas_ku_name);
        description = view.findViewById(R.id.tv_fasilitas_ku_description);
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

    public void bind(Fasilitas fasilitas) {
        this.fasilitas = fasilitas;
        name.setText(fasilitas.getName());
        description.setText(fasilitas.getDescription());
    }

    public void removeEntityFromFirebase() {
        DataUtil.dbFasilitas.child(fasilitas.getKey()).removeValue();
    }

    public void editHolderEntity(View v) {
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        View editView = inflater.inflate(R.layout.crud_fasilitas,null);
        final EditText fasilitasName = editView.findViewById(R.id.fasilitas_name_et);
        fasilitasName.setText(fasilitas.getName());

        final EditText fasilitasDescription = editView.findViewById(R.id.fasilitas_description_et);
        fasilitasDescription.setText(fasilitas.getDescription());

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
        builder.setTitle("Edit")
                .setView(editView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fasilitas.setName(fasilitasName.getText().toString());
                        fasilitas.setDescription(fasilitasDescription.getText().toString());
                        editEntityInFirebase();

                    }
                })
                .setNegativeButton("Cancel",null);
        builder.create().show();
    }

    private void editEntityInFirebase() {
        DataUtil.dbFasilitas.child(fasilitas.getKey()).setValue(fasilitas);
    }

    public void showAToast (View v,String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
