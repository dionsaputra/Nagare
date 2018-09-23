package com.nagare.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nagare.EventDecorator;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Kalender;
import com.nagare.util.DataUtil;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarFragment extends BaseMainFragment{

    private MaterialCalendarView mainCalendar;
    private FloatingActionButton fab;
    private boolean isAcara = true;

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
        setupFloatingButton();
        loadCalendar();
    }

    private void setupMainCalendar() {
        mainCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull final CalendarDay date, boolean selected) {
                ValueEventListener calendarListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean eventExist = false;
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Kalender kalender = item.getValue(Kalender.class);
                            long dbDate = kalender.date;
                            long eventDate = date.getDate().getTime();
                            if(dbDate == eventDate) {
                                eventExist = true;
                                break;
                            }
                        }
                        if(!eventExist) {
                            Toast.makeText(getContext(), "doesn't event", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "event", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                if(isAcara){
                    DataUtil.dbAcara.addListenerForSingleValueEvent(calendarListener);
                } else {
                    DataUtil.dbTemuLurah.addListenerForSingleValueEvent(calendarListener);
                }
            }
        });
    }

    private void setupFloatingButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_acara, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                final EditText etName = inflator.findViewById(R.id.et_nama_acara);
                final EditText etDesc = inflator.findViewById(R.id.et_deskripsi_acara);
                final EditText etTanggal = inflator.findViewById(R.id.et_date_picker);

                etTanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final java.util.Calendar c = java.util.Calendar.getInstance();
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
                                String mKey;
                                if(isAcara){
                                    mKey = DataUtil.dbAcara.push().getKey();
                                } else {
                                    mKey = DataUtil.dbTemuLurah.push().getKey();
                                }
                                String mUserKey = DataUtil.USER_KEY;

                                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("y-M-d");
                                String tDate = etTanggal.getText().toString();
                                long mDate = 0;
                                try {
                                    mDate = mSimpleDateFormat.parse(tDate).getTime();
                                } catch (ParseException e) {}

                                Kalender mKalender = new Kalender(mName, mDesc, mUserKey, mKey, mDate);

                                if(isAcara){
                                    DataUtil.dbAcara.child(mKey).setValue(mKalender);
                                } else {
                                    DataUtil.dbTemuLurah.child(mKey).setValue(mKalender);
                                }
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void loadCalendar() {
        mainCalendar.removeDecorators();

        ValueEventListener calendarListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainCalendar.removeDecorators();
                ArrayList<CalendarDay> events = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Kalender kalender = item.getValue(Kalender.class);
                    events.add(new CalendarDay(new Date(kalender.date)));
                }
                mainCalendar.addDecorator(new EventDecorator(Color.GREEN, events));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if (isAcara) {
            DataUtil.dbAcara.addValueEventListener(calendarListener);
        } else {
            DataUtil.dbTemuLurah.addValueEventListener(calendarListener);
        }
    }

    public void showTemuLurah() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.temu_lurah));
    }

    private void showAcara() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.calendar));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCalendar();
    }

    public boolean isAcara() {
        return isAcara;
    }

    public void setAcara(boolean acara) {
        isAcara = acara;
    }

}