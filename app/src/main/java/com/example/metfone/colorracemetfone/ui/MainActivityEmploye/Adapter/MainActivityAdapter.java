package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.FragmentMap;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.FragmentNightRace;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.FragmentTicket;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class MainActivityAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    public MainActivityAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FragmentTicket.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FragmentNightRace.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
                return FragmentMap.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
