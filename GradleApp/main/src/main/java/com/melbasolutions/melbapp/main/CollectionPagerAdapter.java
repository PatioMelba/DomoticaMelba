package com.melbasolutions.melbapp.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Boris on 2/18/2016.
 */
public class CollectionPagerAdapter extends FragmentPagerAdapter {

    Fragment personalFragment = new PersonalFragment();
    Fragment everyoneFragment = new EveryoneFragment();
    String[] titles = {"Personal", "Everyone"};

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return personalFragment;
            case 1:
                return everyoneFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}