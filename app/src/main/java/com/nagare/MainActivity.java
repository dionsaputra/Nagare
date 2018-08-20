package com.nagare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagare.adapter.SimpleFragmentPagerAdapter;
import com.nagare.fragment.AcaraFragment;
import com.nagare.fragment.Firebase;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.KelilingFragment;
import com.nagare.fragment.LaporFragment;
import com.nagare.fragment.TemuLurahFragment;
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
                    bottomNavbar.setSelectedItemId(R.id.nav_galang_dana);
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
        adapter.addFragment(new KelilingFragment());
        adapter.addFragment(new AcaraFragment());
        adapter.addFragment(new GalangDanaFragment());
        adapter.addFragment(new TemuLurahFragment());
        //adapter.addFragment(new LaporFragment());
        adapter.addFragment(new Firebase());
    }

    private void setupBottomNavbar() {
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_keliling      : viewPager.setCurrentItem(0); break;
                    case R.id.nav_acara         : viewPager.setCurrentItem(1); break;
                    case R.id.nav_galang_dana   : viewPager.setCurrentItem(2); break;
                    case R.id.nav_temu_lurah    : viewPager.setCurrentItem(3); break;
                    //case R.id.nav_lapor         : viewPager.setCurrentItem(4); break;
                    case R.id.nav_firebase      : viewPager.setCurrentItem(4); break;
                }
                return true;
            }
        });
        bottomNavbar.setSelectedItemId(R.id.nav_galang_dana);
    }

}
