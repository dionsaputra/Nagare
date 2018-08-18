package com.nagare.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nagare.fragment.AcaraFragment;
import com.nagare.fragment.GalangDanaFragment;
import com.nagare.fragment.KelilingFragment;
import com.nagare.fragment.LaporFragment;
import com.nagare.fragment.TemuLurahFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private final List<Fragment> fragments = new ArrayList<>();

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
