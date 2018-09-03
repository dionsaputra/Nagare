package com.nagare.fragment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nagare.DetailAcaraActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.util.ViewUtil;

public class CalendarFragment extends BaseMainFragment {

    private CalendarView mainCalendar;
    private ImageView selectedAcaraImage;
    private Toast toast;

    public CalendarFragment() {
        super();
        layoutResId = R.layout.fragment_calendar;
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
        mainCalendar.setDate(System.currentTimeMillis(),false,true);

        mainCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + " " + month + " " + year;
                showAToast(date);
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

    public void showAToast (String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
