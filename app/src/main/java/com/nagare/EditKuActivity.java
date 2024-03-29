package com.nagare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.nagare.adapter.AcaraKuAdapter;
import com.nagare.adapter.FasilitasKuAdapter;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.adapter.GalangDanaKuAdapter;
import com.nagare.adapter.LaporanKuAdapter;
import com.nagare.adapter.LurahKuAdapter;
import com.nagare.model.GalangDana;
import com.nagare.model.Kalender;
import com.nagare.model.Lokasi;
import com.nagare.util.DataUtil;

import java.util.ArrayList;

public class EditKuActivity extends AppCompatActivity {
    private String type;
    private String title;
    private RecyclerView editKuRecyclerView;
    private GalangDanaKuAdapter galangDanaKuAdapter;
    private FasilitasKuAdapter fasilitasKuAdapter;
    private LaporanKuAdapter laporanKuAdapter;
    private LurahKuAdapter lurahKuAdapter;
    private AcaraKuAdapter acaraKuAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Kalender> allKalenderKu;
    private ArrayList<GalangDana> allGalangDanaKu;
    private ArrayList<Lokasi> allLokasiKu;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ku);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("1")){
            title = "Fasilitasku";
        } else if (type.equals("2")){
            title = "Galang Danaku";
        } else if (type.equals("3")){
            title = "Acaraku";
        } else if (type.equals("4")){
            title = "Laporanku";
        } else if (type.equals("5")){
            title = "Jadwalku";
        } else {
            title = "Error";
        }
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    allLokasiKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Lokasi fasilitas = ds.getValue(Lokasi.class);
                        if (fasilitas.getUserKey().equals(DataUtil.USER_KEY)) {
                            allLokasiKu.add(fasilitas);
                        }
                    }
                    fasilitasKuAdapter = new FasilitasKuAdapter(allLokasiKu);
                    editKuRecyclerView.setAdapter(fasilitasKuAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (type.equals("2")){
            editKuRecyclerView = findViewById(R.id.rv_edit);
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            editKuRecyclerView.setLayoutManager(layoutManager);
            editKuRecyclerView.setHasFixedSize(true);

            DataUtil.dbGalangDana.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allGalangDanaKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        GalangDana galangDana = ds.getValue(GalangDana.class);
                        if (galangDana.getUserKey().equals(DataUtil.USER_KEY)) {
                            allGalangDanaKu.add(galangDana);
                        }
                    }
                    galangDanaKuAdapter = new GalangDanaKuAdapter(allGalangDanaKu);
                    editKuRecyclerView.setAdapter(galangDanaKuAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (type.equals("3")){
            editKuRecyclerView = findViewById(R.id.rv_edit);
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            editKuRecyclerView.setLayoutManager(layoutManager);
            editKuRecyclerView.setHasFixedSize(true);

            DataUtil.dbAcara.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allKalenderKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Kalender acara = ds.getValue(Kalender.class);
                        if (acara.getUserKey().equals(DataUtil.USER_KEY)) {
                            allKalenderKu.add(acara);
                        }
                    }
                    acaraKuAdapter = new AcaraKuAdapter(allKalenderKu);
                    editKuRecyclerView.setAdapter(acaraKuAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("4")){
            editKuRecyclerView = findViewById(R.id.rv_edit);
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            editKuRecyclerView.setLayoutManager(layoutManager);
            editKuRecyclerView.setHasFixedSize(true);

            DataUtil.dbLapor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allLokasiKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Lokasi laporan = ds.getValue(Lokasi.class);
                        if (laporan.getUserKey().equals(DataUtil.USER_KEY)) {
                            allLokasiKu.add(laporan);
                        }
                    }
                    laporanKuAdapter = new LaporanKuAdapter(allLokasiKu);
                    editKuRecyclerView.setAdapter(laporanKuAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("5")){
            editKuRecyclerView = findViewById(R.id.rv_edit);
            layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            editKuRecyclerView.setLayoutManager(layoutManager);
            editKuRecyclerView.setHasFixedSize(true);

            DataUtil.dbTemuLurah.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    allKalenderKu = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Kalender temuLurah = ds.getValue(Kalender.class);
                        if(DataUtil.USER_LURAH){
                            allKalenderKu.add(temuLurah);
                        } else {
                            if (temuLurah.getUserKey().equals(DataUtil.USER_KEY)) {
                                allKalenderKu.add(temuLurah);
                            }
                        }

                    }
                    lurahKuAdapter = new LurahKuAdapter(allKalenderKu);
                    editKuRecyclerView.setAdapter(lurahKuAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
