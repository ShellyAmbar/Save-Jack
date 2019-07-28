package com.shellyambar.thegame.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shellyambar.thegame.Fragments.AllLevelsFragment;
import com.shellyambar.thegame.Fragments.AllScoresFragment;
import com.shellyambar.thegame.Fragments.AllUsersFragment;


public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PageAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new AllUsersFragment();
            case 1:
                return new AllScoresFragment();
            case 2:
                return new AllLevelsFragment();
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
