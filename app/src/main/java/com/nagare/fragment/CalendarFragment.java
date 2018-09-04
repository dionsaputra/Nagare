package com.nagare.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nagare.DetailAcaraActivity;
import com.nagare.EventDecorator;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Acara;
import com.nagare.util.DataUtil;
import com.nagare.util.ViewUtil;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

public class CalendarFragment extends BaseMainFragment {

    private MaterialCalendarView mainCalendar;
    private ImageView selectedAcaraImage;

    public CalendarFragment() {
        super();
        layoutResId = R.layout.fragment_calendar;
        DataUtil.getInstance().generateDummyAcaras();
    }

    @Override
    protected void initComponent() {
        mainCalendar = rootView.findViewById(R.id.cv_main_calendar);
        selectedAcaraImage = rootView.findViewById(R.id.iv_selected_acara);
        ViewUtil.loadImage(getContext(), selectedAcaraImage, R.drawable.itb);
    }

    @Override
    protected void setupComponent() {
        setupMainCalendar();
        setupSelectedAcaraImage();
    }

    private void setupMainCalendar() {
        ArrayList<CalendarDay> events = new ArrayList<>();
        for (Acara acara : DataUtil.getInstance().acaras) {
            events.add(acara.getDate());
        }

        mainCalendar.addDecorator(new EventDecorator(Color.GREEN, events));

//        mainCalendar.setDate(System.currentTimeMillis(),false,true);
//
//        mainCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String date = dayOfMonth + " " + month + " " + year;
//                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
//            }
//        });
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
