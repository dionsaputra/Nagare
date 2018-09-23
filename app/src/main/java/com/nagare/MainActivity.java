package com.nagare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nagare.adapter.SimpleFragmentPagerAdapter;
import com.nagare.auth.LoginActivity;
import com.nagare.fragment.CalendarFragment;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.MapsFragment;
import com.nagare.util.DataUtil;

public class MainActivity extends AppCompatActivity{

    private static final int ACTIVE_FRAGMENT_MAPS = 0;
    private static final int ACTIVE_FRAGMENT_CALENDAR = 1;
    private static final int ACTIVE_FRAGMENT_GALANG_DANA = 2;

    private Context context = this;

    private BottomNavigationView bottomNavbar;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private Toast toast;

    private MenuItem activeMenuItem;
    private int activeFragment;

    SimpleFragmentPagerAdapter adapter;

    private MapsFragment mapsFragment;
    private CalendarFragment calendarFragment;
    private GalangDanaFragment galangDanaFragment;

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
            this.moveTaskToBack(true);
        }
    }

    private void initComponent() {
        viewPager = findViewById(R.id.view_pager);
        bottomNavbar = findViewById(R.id.bottom_navbar);
    }

    private void setupComponent() {
        setupSideNavbar();
        setupViewPager();
        setupBottomNavbar();
        setupActionBar(R.array.calendar_options);
    }

    private void setupSideNavbar(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        Intent editIntent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_side_fasilitas:
                                editIntent = new Intent(MainActivity.this, EditKuActivity.class);
                                editIntent.putExtra("type","1");
                                startActivity(editIntent);
                                return true;
                            case R.id.nav_side_galang_dana:
                                editIntent = new Intent(MainActivity.this, EditKuActivity.class);
                                editIntent.putExtra("type","2");
                                startActivity(editIntent);
                                return true;
                            case R.id.nav_side_acara:
                                editIntent = new Intent(MainActivity.this, EditKuActivity.class);
                                editIntent.putExtra("type","3");
                                startActivity(editIntent);
                                return true;
                            case R.id.nav_side_lapor:
                                editIntent = new Intent(MainActivity.this, EditKuActivity.class);
                                editIntent.putExtra("type","4");
                                startActivity(editIntent);
                                return true;
                            case R.id.nav_side_temu_lurah:
                                editIntent = new Intent(MainActivity.this, EditKuActivity.class);
                                editIntent.putExtra("type","5");
                                startActivity(editIntent);
                                return true;
                            case R.id.nav_side_logout:
                                editIntent = new Intent(MainActivity.this, LoginActivity.class);
                                SharedPreferences spLogin;
                                spLogin = getSharedPreferences("login",MODE_PRIVATE);
                                spLogin.edit().putBoolean("logged",false).apply();
                                spLogin.edit().putString("userKey","").apply();
                                DataUtil.USER_KEY = "";
                                MapsFragment.mapReady = false;
                                finish();
                                startActivity(editIntent);
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

        adapter = new SimpleFragmentPagerAdapter(context, getSupportFragmentManager());
        addAllFragments(adapter);
        viewPager.setAdapter(adapter);

    }

    public void setupActionBar(final int optionsResId) {
        final Spinner actionBarSpinner = findViewById(R.id.custom_action_bar_spinner);
        TextView actionBarTitle = findViewById(R.id.custom_action_bar_title);

        if (optionsResId == R.array.calendar_options || optionsResId == R.array.maps_options) {
            actionBarTitle.setVisibility(View.GONE);
            actionBarSpinner.setVisibility(View.VISIBLE);
            ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(context, optionsResId, R.layout.title_spinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            actionBarSpinner.setAdapter(spinnerAdapter);
            actionBarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (optionsResId == R.array.maps_options) {
                        if (position == 0) {
                            mapsFragment.setKeliling(true);
                        } else {
                            mapsFragment.setKeliling(false);
                        }
                        mapsFragment.loadLokasi();
                    } else if (optionsResId == R.array.calendar_options){
                        if (position == 0) {
                            calendarFragment.setAcara(true);
                        } else {
                            calendarFragment.setAcara(false);
                        }
                        calendarFragment.loadCalendar();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mapsFragment.setKeliling(true);
                    mapsFragment.loadLokasi();
                }
            });
        } else {
            actionBarSpinner.setVisibility(View.GONE);
            actionBarTitle.setVisibility(View.VISIBLE);
        }
    }

    private void addAllFragments(SimpleFragmentPagerAdapter adapter) {
        mapsFragment = new MapsFragment();
        mapsFragment.setKeliling(true);

        calendarFragment = new CalendarFragment();
        galangDanaFragment = new GalangDanaFragment();

        adapter.addFragment(mapsFragment);
        adapter.addFragment(calendarFragment);
        adapter.addFragment(galangDanaFragment);
    }

    private void setupBottomNavbar() {
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_maps          : viewPager.setCurrentItem(0); break;
                    case R.id.nav_calendar      : viewPager.setCurrentItem(1); break;
                    case R.id.nav_galang_dana   : viewPager.setCurrentItem(2); break;
                }
                return true;
            }
        });
        bottomNavbar.setSelectedItemId(R.id.nav_calendar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MapsFragment.mapReady){
            mapsFragment.loadLokasi();
        }
    }

    public MapsFragment getMapsFragment() {
        return mapsFragment;
    }

    public void setMapsFragment(MapsFragment mapsFragment) {
        this.mapsFragment = mapsFragment;
    }
}
