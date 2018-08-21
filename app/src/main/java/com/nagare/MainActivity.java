package com.nagare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nagare.adapter.SimpleFragmentPagerAdapter;
import com.nagare.fragment.CalendarFragment;
import com.nagare.fragment.Firebase;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.MapsFragment;
import com.nagare.util.ViewUtil;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private ImageView nagareLogo;
    private BottomNavigationView bottomNavbar;
    private ViewPager viewPager;

    private MenuItem activeMenuItem;

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
                if (activeMenuItem != null) {
                    activeMenuItem.setChecked(false);
                } else {
                    bottomNavbar.setSelectedItemId(R.id.nav_maps);
                }
                activeMenuItem = bottomNavbar.getMenu().getItem(position);
                activeMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(context, getSupportFragmentManager());
        addAllFragments(adapter);
        viewPager.setAdapter(adapter);
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

}
