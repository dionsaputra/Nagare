package com.nagare;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.adapter.SimpleFragmentPagerAdapter;
import com.nagare.auth.LoginActivity;
import com.nagare.fragment.CalendarFragment;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.MapsFragment;
import com.nagare.model.User;
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
    private TextView header_name;
    private TextView header_email;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.nav_header, null);

        header_name = hView.findViewById(R.id.nav_header_name);
        header_name.setText(DataUtil.USER_NAMA);

        header_email = hView.findViewById(R.id.nav_header_email);
        header_email.setText(DataUtil.USER_EMAIL);

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
                            case R.id.nav_side_pengaturan:
                                final User user = new User(DataUtil.USER_NAMA, DataUtil.USER_EMAIL, DataUtil.USER_PASSWORD);
                                user.setKey(DataUtil.USER_KEY);
                                user.setLurah(DataUtil.USER_LURAH);
                                LayoutInflater inflater = getLayoutInflater();
                                View editView = inflater.inflate(R.layout.dialog_user,null);
                                final EditText userName = editView.findViewById(R.id.et_nama_user);
                                userName.setText(user.getName());

                                final EditText userEmail = editView.findViewById(R.id.et_email_user);
                                userEmail.setText(user.getEmail());

                                final EditText userPassword = editView.findViewById(R.id.et_password_user);
//                                userPassword.setText(user.getPassword());

                                final EditText userConfirm = editView.findViewById(R.id.et_confirm_user);
//                                userConfirm.setText(user.getPassword());
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                builder.setView(editView)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(userPassword.getText().toString().equals(userConfirm.getText().toString())){
                                                    user.setName(userName.getText().toString());
                                                    user.setEmail(userEmail.getText().toString());
                                                    user.setPassword(userPassword.getText().toString());
                                                    DataUtil.USER_NAMA = user.getName();
                                                    DataUtil.USER_EMAIL = user.getEmail();
                                                    DataUtil.USER_PASSWORD = user.getPassword();
                                                    SharedPreferences spLogin;
                                                    spLogin = getSharedPreferences("login",MODE_PRIVATE);
                                                    spLogin.edit().putString("userNama",DataUtil.USER_NAMA).apply();
                                                    spLogin.edit().putString("userPassword",DataUtil.USER_PASSWORD).apply();
                                                    spLogin.edit().putString("userEmail",DataUtil.USER_EMAIL).apply();
                                                    header_name.setText(DataUtil.USER_NAMA);
                                                    header_email.setText(DataUtil.USER_EMAIL);
                                                    DataUtil.dbUser.child(user.getKey()).setValue(user);
                                                } else {
                                                    Toast.makeText(context,"Password tidak sama", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel",null);
                                builder.create().show();
                                return true;
                            case R.id.nav_side_logout:
                                editIntent = new Intent(MainActivity.this, LoginActivity.class);
                                SharedPreferences spLogin;
                                spLogin = getSharedPreferences("login",MODE_PRIVATE);
                                spLogin.edit().putBoolean("logged",false).apply();
                                spLogin.edit().putString("userKey","").apply();
                                spLogin.edit().putBoolean("userLurah",false).apply();
                                DataUtil.USER_KEY = "";
                                DataUtil.USER_LURAH = false;
                                DataUtil.USER_NAMA = "";
                                DataUtil.USER_EMAIL = "";
                                DataUtil.USER_PASSWORD = "";
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
