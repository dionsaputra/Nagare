package com.nagare.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nagare.DetailAcaraActivity;
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.util.ViewUtil;

public class CalendarFragment extends Fragment {

    private View rootView;
    private CalendarView mainCalendar;
    private ImageView selectedAcaraImage;

    public CalendarFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        setupComponent();
    }

    private void initComponent() {
        mainCalendar = rootView.findViewById(R.id.cv_main_calendar);
        selectedAcaraImage = rootView.findViewById(R.id.iv_selected_acara);
        ViewUtil.loadImage(getContext(), selectedAcaraImage, R.drawable.itb);
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.calendar_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.acara_action) {
//            showAcara();
//        } else {
//            showTemuLurah();
//        }
//        return true;
//    }

    private void setupComponent() {
        setupMainCalendar();
        setupSelectedAcaraImage();
    }

    private void setupMainCalendar() {
        mainCalendar.setDate(System.currentTimeMillis(),false,true);

        mainCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + " " + month + " " + year;
                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
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
