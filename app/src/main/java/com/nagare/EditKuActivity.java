package com.nagare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import java.util.ArrayList;

public class EditKuActivity extends AppCompatActivity implements GalangDanaAdapter.GalangDanaClickHandler{
    private String type;
    private String title;
    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;
    private GalangDanaAdapter.GalangDanaClickHandler clickHandler = this;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initComponent();
        setupComponent();
    }

    protected void initComponent() {
        if (type.equals("1")){

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

    @Override
    public void onClick(int pos) {

    }
}
