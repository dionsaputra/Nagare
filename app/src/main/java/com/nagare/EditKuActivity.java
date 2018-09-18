package com.nagare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.model.Fasilitas;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import java.util.ArrayList;

public class EditKuActivity extends AppCompatActivity {
    private String type;
    private String title;
    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ku);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("1")){
            title = "Fasilitas Ku";
        } else if (type.equals("2")){
            title = "Galang Dana Ku";
        } else if (type.equals("3")){
            title = "Acara Ku";
        } else if (type.equals("4")){
            title = "Laporan Ku";
        } else if (type.equals("5")){
            title = "Jadwal Ku";
        } else {
            title = "Error";
        }
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Alert Dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View inflator = getLayoutInflater().inflate(R.layout.dialog_fasilitas, null);

        final EditText name = inflator.findViewById(R.id.et_nama_fasilitas);
        final EditText desc = inflator.findViewById(R.id.et_deskripsi_fasilitas);

        alertDialogBuilder.setTitle(R.string.fasilitas)
                .setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();


        // FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        initComponent();
        setupComponent();
    }

    protected void initComponent() {
        if (type.equals("1")){
//            DataUtil.dbFasilitas.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                        Fasilitas fasilitas = ds.getValue(Fasilitas.class);
//                        if (fasilitas.getUserKey() == DataUtil.USER_KEY) {
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        } else if (type.equals("2")){
//            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//            galangDanaRecyclerView = findViewById(R.id.rv_edit);
//
//            DataUtil.getInstance().dbGalangDana.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot item : dataSnapshot.getChildren()) {
//                        galangDanaAdapter.galangDanas.add(item.getValue(GalangDana.class));
//                    }
//                    galangDanaAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        } else if (type.equals("3")){

        } else if (type.equals("4")){

        } else if (type.equals("5")){

        } else {
            title = "Error";
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setupComponent() {
//        galangDanaRecyclerView.setLayoutManager(layoutManager);
//        galangDanaRecyclerView.setHasFixedSize(true);
//        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

}
