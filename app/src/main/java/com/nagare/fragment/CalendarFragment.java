package com.nagare.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.EventDecorator;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Kalender;
import com.nagare.model.User;
import com.nagare.util.DataUtil;

import com.nagare.util.ViewUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarFragment extends BaseMainFragment implements OnDateSelectedListener{

    private MaterialCalendarView mainCalendar;
    private FloatingActionButton fab;
    private boolean isAcara = true;
    private ArrayList<Kalender> acaras = new ArrayList<>();

    public CalendarFragment() {
        super();
        layoutResId = R.layout.fragment_calendar;
    }

    @Override
    protected void initComponent() {
        mainCalendar = rootView.findViewById(R.id.cv_main_calendar);
        mainCalendar.setOnDateChangedListener(this);
        fab = rootView.findViewById(R.id.fab);
    }

    @Override
    protected void setupComponent() {
        setupFloatingButton();
        loadCalendar();
    }

    private void setupFloatingButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAcara && DataUtil.USER_LURAH){
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
                View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_acara, null);
                TextView titleDialog = inflator.findViewById(R.id.dialog_acara_title);
                if(!isAcara){
                    titleDialog.setText("Jadwal Lurah");
                }
                ImageView imageDialog = inflator.findViewById(R.id.dialog_acara_image);
                if(!isAcara){
                    imageDialog.setImageDrawable(getResources().getDrawable(R.drawable.temu_lurah));
                }
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
                                        etTanggal.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
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

                                Kalender mKalender = new Kalender(mName, mDesc, mUserKey, mKey, mDate, 0);

                                if(isAcara){
                                    DataUtil.dbAcara.child(mKey).setValue(mKalender);
                                } else {
                                    DataUtil.dbTemuLurah.child(mKey).setValue(mKalender);
                                }
                                loadCalendar();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void loadCalendar() {
        mainCalendar.removeDecorators();
        acaras.clear();
        if(!isAcara && DataUtil.USER_LURAH){
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
        ValueEventListener calendarListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainCalendar.removeDecorators();
                ArrayList<CalendarDay> events = new ArrayList<>();
                acaras.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Kalender kalender = item.getValue(Kalender.class);
                    events.add(new CalendarDay(new Date(kalender.getDate())));
                    acaras.add(kalender);
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

    private void setDialogData(View view, Kalender acara, int size) {
        final TextView title = view.findViewById(R.id.tv_acara_name);
        final TextView counter = view.findViewById(R.id.tv_counter);
        final TextView owner = view.findViewById(R.id.tv_acara_owner);
        final TextView description = view.findViewById(R.id.tv_acara_description);

        title.setText(acara.getTitle());
        counter.setText(String.valueOf(1) + "/" + String.valueOf(size));

        Query query = DataUtil.dbUser.child(acara.getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                owner.setText(dataSnapshot.getValue(User.class).getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        description.setText(acara.getDescription());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final ArrayList<Kalender> currentAcaras = new ArrayList<>();
        for (Kalender acara : acaras) {
            if (acara.getDate() == date.getDate().getTime()) {
                currentAcaras.add(acara);
            }
        }

        if (currentAcaras.size() == 0) return;

        LayoutInflater inflater = ((AppCompatActivity)getContext()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.detail_acara,null);

        setDialogData(view, currentAcaras.get(0), currentAcaras.size());

        final ImageView leftArrow = view.findViewById(R.id.arrow_left);
        final ImageView rightArrow = view.findViewById(R.id.arrow_right);

        if (currentAcaras.size() < 2) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.GONE);
        } else {
            final int[] position = {0};
            leftArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position[0]--;
                    if (0 <= position[0] && position[0] < currentAcaras.size()) {
                        setDialogData(view, currentAcaras.get(position[0]), currentAcaras.size());
                    }
                }
            });

            rightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position[0]++;
                    if (0 <= position[0] && position[0] < currentAcaras.size()) {
                        setDialogData(view, currentAcaras.get(position[0]), currentAcaras.size());
                    }
                }
            });
        }

        ImageView imageView = view.findViewById(R.id.iv_selected_acara);
        ViewUtil.loadImage(getContext(),imageView,R.drawable.itb);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        builder.create().show();
    }
}