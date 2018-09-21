package com.nagare.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
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

import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends BaseMainFragment{

    private MaterialCalendarView mainCalendar;
    private FloatingActionButton fab;

    public CalendarFragment() {
        super();
        layoutResId = R.layout.fragment_calendar;
    }

    @Override
    protected void initComponent() {
        mainCalendar = rootView.findViewById(R.id.cv_main_calendar);
        fab = rootView.findViewById(R.id.fab);
    }

    @Override

    protected void setupComponent() {
        setupMainCalendar();
    }

    private void setupMainCalendar() {

        mainCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {

                DataUtil.dbAcara.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean eventExist = false;
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Acara acara = item.getValue(Acara.class);
                            long dbDate = acara.date;
                            long eventDate = date.getDate().getTime();
                            if(dbDate == eventDate) {
                                eventExist = true;
                                break;
                            }
                        }
                        if(!eventExist) {

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_acara, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                final EditText etName = inflator.findViewById(R.id.et_nama_fasilitas);
                final EditText etDesc = inflator.findViewById(R.id.et_deskripsi_fasilitas);
                final EditText etTanggal = inflator.findViewById(R.id.et_date_picker);

                etTanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        // Random initial selected date
                        int mYear = 2010, mMonth = 1, mDay = 2;
                        DatePickerDialog dpd = new DatePickerDialog(getContext(),
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

                alertDialogBuilder.setView(inflator)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mName = etName.getText().toString();
                                String mDesc = etDesc.getText().toString();
                                String mKey = DataUtil.dbAcara.push().getKey();
                                String mUserKey = DataUtil.USER_KEY;

                                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("y-M-d");
                                String tDate = etTanggal.getText().toString();
                                long mDate = 0;
                                try {
                                    mDate = mSimpleDateFormat.parse(tDate).getTime();
                                } catch (ParseException e) {}

                                Acara mAcara = new Acara(mName, mDesc, mUserKey, mKey, mDate);

                                DataUtil.dbAcara.child(mKey).setValue(mAcara);
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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

    public void showTemuLurah() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.temu_lurah));
    }

    private void showAcara() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.acara));
    }


}