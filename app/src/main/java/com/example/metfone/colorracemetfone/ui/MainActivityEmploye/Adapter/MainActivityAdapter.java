package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.MapFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.NightRaceFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.TicketFragment;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class MainActivityAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };

    public MainActivityAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return TicketFragment.newInstance(null);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return NightRaceFragment.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
                return MapFragment.newInstance(null, null);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
