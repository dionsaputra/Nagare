package com.nagare.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.nagare.R;
import com.nagare.adapter.GalangDanaAdapter;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.GalangDana;
import com.nagare.util.DataUtil;

import android.support.design.widget.FloatingActionButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GalangDanaFragment extends BaseMainFragment{

    private RecyclerView galangDanaRecyclerView;
    private GalangDanaAdapter galangDanaAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<GalangDana> allGalangDana;
    private FloatingActionButton fab;

    public GalangDanaFragment() {
        super();
        layoutResId = R.layout.fragment_galang_dana;
    }

    @Override
    protected void initComponent() {
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        galangDanaRecyclerView = rootView.findViewById(R.id.rv_galang_dana);

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGalangDanaDialog();
            }
        });

    }

    private void showAddGalangDanaDialog() {
        View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_galang_dana, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final EditText gdName = inflator.findViewById(R.id.et_nama_galang_dana);
        final EditText gdDesc = inflator.findViewById(R.id.et_deskripsi_galang_dana);
        final EditText gdNominal = inflator.findViewById(R.id.et_target_galang_dana);
        final EditText gdDeadline = inflator.findViewById(R.id.et_deadline_galang_dana);

        gdDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(gdDeadline);
            }
        });

        builder.setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = gdName.getText().toString();
                        String desc = gdDesc.getText().toString();
                        Long nominal = 0L;

                        if (gdNominal.getText().toString().length() > 0) {
                            nominal = Long.parseLong(gdNominal.getText().toString());
                        }

                        String key = DataUtil.dbGalangDana.push().getKey();
                        String userKey = DataUtil.USER_KEY;

                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("y-M-d");
                        String strDate = gdDeadline.getText().toString();

                        long deadline = 0;
                        try {
                            deadline = mSimpleDateFormat.parse(strDate).getTime();
                        } catch (ParseException e) {}

                        GalangDana galangDana = new GalangDana(name, desc, nominal, deadline);
                        galangDana.setCurrentDana(0L);
                        galangDana.setKey(key);
                        galangDana.setUserKey(userKey);

                        DataUtil.dbGalangDana.child(key).setValue(galangDana);
                    }
                });
        builder.create().show();
    }

    private void showDatePicker(final EditText deadline) {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        // Random initial selected date
        int mYear = 2010, mMonth = 1, mDay = 2;
        DatePickerDialog dpd = new DatePickerDialog(getContext(),
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

    @Override
    protected void setupComponent() {
        galangDanaRecyclerView.setLayoutManager(layoutManager);
        galangDanaRecyclerView.setHasFixedSize(true);

        DataUtil.dbGalangDana.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllGalangDana(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllGalangDana(DataSnapshot dataSnapshot) {
        allGalangDana = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            allGalangDana.add(ds.getValue(GalangDana.class));
        }
        galangDanaAdapter = new GalangDanaAdapter(allGalangDana);
        galangDanaRecyclerView.setAdapter(galangDanaAdapter);
    }

}
