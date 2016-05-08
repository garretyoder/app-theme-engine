package com.afollestad.appthemeenginesample.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.create(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format("TAB%d", position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
