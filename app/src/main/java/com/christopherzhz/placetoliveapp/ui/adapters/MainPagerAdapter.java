package com.christopherzhz.placetoliveapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.christopherzhz.placetoliveapp.ui.fragments.AccountFragment;
import com.christopherzhz.placetoliveapp.ui.fragments.MapViewFragment;
import com.christopherzhz.placetoliveapp.ui.fragments.RoomInfoFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new RoomInfoFragment();
            case 1:
                return new MapViewFragment();
            case 2:
                return new AccountFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

}
