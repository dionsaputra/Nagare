package com.nagare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagare.activity.Acara;
import com.nagare.activity.GalangDana;
import com.nagare.activity.Keliling;
import com.nagare.activity.Lapor;
import com.nagare.activity.TemuLurah;
import com.nagare.fragment.AcaraFragment;
import com.nagare.fragment.KelilingFragment;
import com.nagare.util.ViewUtil;

public class MainActivity extends AppCompatActivity {

    private Context context = this;

    private ImageView nagareLogo;
    private BottomNavigationView bottomNavbar;
    private BottomNavigationView.OnNavigationItemSelectedListener navbarItemListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

//        prepareOtherActivity(R.id.tv_temu_lurah, TemuLurah.class);
//        prepareOtherActivity(R.id.tv_keliling, Keliling.class);
//        prepareOtherActivity(R.id.tv_lapor, Lapor.class);
//        prepareOtherActivity(R.id.tv_acara, Acara.class);
//        prepareOtherActivity(R.id.tv_galang_dana, GalangDana.class);
//        prepareOtherActivity(R.id.tv_stub_1, MapsActivity.class);

    }

    private void initComponent() {
        nagareLogo = findViewById(R.id.iv_nagare_logo);
        ViewUtil.loadImage(context, nagareLogo, R.drawable.nagare_logo);

        navbarItemListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_keliling:
                        fragment = new KelilingFragment();
                        ViewUtil.loadFragment(context, fragment, R.id.frame_fragment);
                        return true;
                    case R.id.nav_acara:
                        fragment = new AcaraFragment();
                        ViewUtil.loadFragment(context, fragment, R.id.frame_fragment);
                        return true;
                    case R.id.nav_temu_lurah    : return true;
                    case R.id.nav_lapor         : return true;
                    case R.id.nav_galang_dana   : return true;
                }
                return false;
            }
        };

        bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setOnNavigationItemSelectedListener(navbarItemListener);
        bottomNavbar.setSelectedItemId(R.id.nav_temu_lurah);
    }



    private void prepareOtherActivity(int textViewResId, final Class intentClass) {
        TextView textView = findViewById(textViewResId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, intentClass);
                startActivity(intent);
            }
        });
    }
}
