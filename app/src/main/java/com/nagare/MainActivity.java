package com.nagare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private DrawerLayout mDrawerLayout;
    private Toast toast;

    private MenuItem activeMenuItem;
    private int activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        setupComponent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initComponent() {
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);
        viewPager = findViewById(R.id.view_pager);
        bottomNavbar = findViewById(R.id.bottom_navbar);
    }

    private void setupComponent() {
        setupSideNavbar();
        setupViewPager();
        setupBottomNavbar();
        setupActionBar(R.array.maps_options);
    }

    private void setupSideNavbar(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
//                        menuItem.setChecked(false);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_side_fasilitas:
                                showAToast("Fasilitas Ku");
                                Intent i = new Intent(MainActivity.this, FasilitasKuActivity.class);
                                startActivity(i);
                                return true;
                            case R.id.nav_side_galang_dana:
                                showAToast("Galang Dana Ku");
                                return true;
                            case R.id.nav_side_acara:
                                showAToast("Acara Ku");
                                return true;
                            case R.id.nav_side_lapor:
                                showAToast("Laporan Ku");
                                return true;
                            case R.id.nav_side_temu_lurah:
                                showAToast("Jadwal Ku");
                                return true;

                        }

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();

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
//        if (getSupportActionBar() == null) return;
//
        Spinner actionBarSpinner = findViewById(R.id.custom_action_bar_spinner);
        TextView actionBarTitle = findViewById(R.id.custom_action_bar_title);

        if (optionsResId == R.array.calendar_options || optionsResId == R.array.maps_options) {
            actionBarTitle.setVisibility(View.GONE);
            actionBarSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(context, optionsResId, R.layout.title_spinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            actionBarSpinner.setAdapter(spinnerAdapter);
        } else {
            actionBarSpinner.setVisibility(View.GONE);
            actionBarTitle.setVisibility(View.VISIBLE);
        }
//
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
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
    }

    public void showAToast (String message){
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
