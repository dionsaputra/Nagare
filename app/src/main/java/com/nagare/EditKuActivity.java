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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.adapter.FasilitasKuAdapter;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.model.Lokasi;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import java.util.ArrayList;

public class EditKuActivity extends AppCompatActivity {
    private String type;
    private String title;
    private RecyclerView editKuRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private FasilitasKuAdapter fasilitasKuAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Lokasi> allFasilitasKu;
    private Toast toast;

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
        if(type.equals("1") || type.equals("4")){
            fab.setVisibility(View.INVISIBLE);
        }

        initComponent();
        setupComponent();
        showAToast("Tekan item untuk edit/delete.");
    }

    protected void initComponent() {
        if (type.equals("1")){
            editKuRecyclerView = findViewById(R.id.rv_edit);
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            editKuRecyclerView.setLayoutManager(layoutManager);
            editKuRecyclerView.setHasFixedSize(true);

            DataUtil.dbFasilitas.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allFasilitasKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Lokasi fasilitas = ds.getValue(Lokasi.class);
                        if (fasilitas.getUserKey().equals(DataUtil.USER_KEY)) {
                            allFasilitasKu.add(fasilitas);
                        }
                    }
                    fasilitasKuAdapter = new FasilitasKuAdapter(allFasilitasKu);
                    editKuRecyclerView.setAdapter(fasilitasKuAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
    }

    public void showAToast (String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
