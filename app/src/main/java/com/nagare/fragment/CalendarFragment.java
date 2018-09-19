package com.nagare.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.DetailAcaraActivity;
import com.nagare.EventDecorator;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Acara;
import com.nagare.model.Lokasi;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class CalendarFragment extends BaseMainFragment{

    private MaterialCalendarView mainCalendar;

    private ImageView selectedAcaraImage;
    private TextView acaraName;
    private TextView acaraOwner;

    public CalendarFragment() {
        super();
        layoutResId = R.layout.fragment_calendar;
//        DataUtil.getInstance().generateDummyAcaras();
    }

    @Override
    protected void initComponent() {
        mainCalendar = rootView.findViewById(R.id.cv_main_calendar);

        selectedAcaraImage = rootView.findViewById(R.id.iv_selected_acara);
        acaraName = rootView.findViewById(R.id.tv_acara_title);
        acaraOwner = rootView.findViewById(R.id.tv_acara_pengelola);

        ViewUtil.loadImage(getContext(), selectedAcaraImage, R.drawable.itb);
    }

    @Override
    protected void setupComponent() {
        setupMainCalendar();
        setupSelectedAcaraImage();
    }

    private void setupMainCalendar() {

        mainCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                final View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_acara, null);
                final EditText name = inflator.findViewById(R.id.et_nama_fasilitas);
                final EditText desc = inflator.findViewById(R.id.et_deskripsi_fasilitas);

                DataUtil.dbAcara.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean eventExist = false;
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            long dbDate = item.getValue(Acara.class).date;
                            long eventDate = date.getDate().getTime();
                            if(dbDate == eventDate) {
                                eventExist = true;
                                acaraName.setText(item.getValue(Acara.class).title);
                                acaraOwner.setText(item.getValue(Acara.class).userKey);
                                break;
                            }
                        }
                        if(!eventExist) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                            alertDialogBuilder.setView(inflator)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String key = DataUtil.dbAcara.push().getKey();
                                            Acara acara = new Acara(name.getText().toString(), desc.getText().toString(), DataUtil.USER_KEY, key, date.getDate().getTime());
                                            DataUtil.dbAcara.child(key).setValue(acara);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        DataUtil.dbAcara.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainCalendar.removeDecorators();
                ArrayList<CalendarDay> events = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Acara acara = item.getValue(Acara.class);
                    events.add(new CalendarDay(new Date(acara.date)));
                }
                mainCalendar.addDecorator(new EventDecorator(Color.GREEN, events));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupSelectedAcaraImage() {
        selectedAcaraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.startNewActivity(getContext(), DetailAcaraActivity.class, selectedAcaraImage, R.string.tn_selected_acara);
            }
        });
    }

    public void showTemuLurah() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.temu_lurah));
    }

    private void showAcara() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.acara));
    }


}