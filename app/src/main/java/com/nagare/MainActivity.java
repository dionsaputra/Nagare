package com.nagare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nagare.adapter.SimpleFragmentPagerAdapter;
import com.nagare.fragment.CalendarFragment;
import com.nagare.fragment.Firebase;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.MapsFragment;
import com.nagare.util.ViewUtil;

public class MainActivity extends AppCompatActivity{

    private static final int ACTIVE_FRAGMENT_MAPS = 0;
    private static final int ACTIVE_FRAGMENT_CALENDAR = 1;
    private static final int ACTIVE_FRAGMENT_GALANG_DANA = 2;

    private Context context = this;

    private ImageView nagareLogo;
    private BottomNavigationView bottomNavbar;
    private ViewPager viewPager;

    private MenuItem activeMenuItem;
    private int activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        setupComponent();
    }

    private void initComponent() {
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
        viewPager = findViewById(R.id.view_pager);
        bottomNavbar = findViewById(R.id.bottom_navbar);
    }

    private void setupComponent() {
        setupViewPager();
        setupBottomNavbar();
    }

    private void setupViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (activeMenuItem != null) activeMenuItem.setChecked(false);

                activeMenuItem = bottomNavbar.getMenu().getItem(position);
                activeMenuItem.setChecked(true);
                activeFragment = position;

                switch (activeFragment) {
                    case ACTIVE_FRAGMENT_MAPS:      setupActionBar(R.array.maps_options); break;
                    case ACTIVE_FRAGMENT_CALENDAR:  setupActionBar(R.array.calendar_options); break;
                    default:                        setupActionBar(-1); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(context, getSupportFragmentManager());
        addAllFragments(adapter);
        viewPager.setAdapter(adapter);
    }

    public void setupActionBar(int optionsResId) {
        if (getSupportActionBar() == null) return;

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        Spinner actionBarSpinner = getSupportActionBar().getCustomView().findViewById(R.id.custom_action_bar_spinner);
        TextView actionBarTitle = getSupportActionBar().getCustomView().findViewById(R.id.custom_action_bar_title);

        if (optionsResId == R.array.calendar_options || optionsResId == R.array.maps_options) {
            actionBarTitle.setVisibility(View.GONE);
            ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(context, optionsResId, R.layout.title_spinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            actionBarSpinner.setAdapter(spinnerAdapter);
        } else {
            actionBarSpinner.setVisibility(View.GONE);
            actionBarTitle.setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
    }

    private void addAllFragments(SimpleFragmentPagerAdapter adapter) {
        adapter.addFragment(new MapsFragment());
        adapter.addFragment(new CalendarFragment());
        adapter.addFragment(new GalangDanaFragment());
        adapter.addFragment(new Firebase());
    }

    private void setupBottomNavbar() {
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_maps          : viewPager.setCurrentItem(0); break;
                    case R.id.nav_calendar      : viewPager.setCurrentItem(1); break;
                    case R.id.nav_galang_dana   : viewPager.setCurrentItem(2); break;
                    case R.id.nav_firebase      : viewPager.setCurrentItem(3); break;
                }
                return true;
            }
        });
        bottomNavbar.setSelectedItemId(R.id.nav_maps);
        setupActionBar(R.array.maps_options);
    }
}
