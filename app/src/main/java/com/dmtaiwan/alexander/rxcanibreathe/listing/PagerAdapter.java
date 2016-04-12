package com.dmtaiwan.alexander.rxcanibreathe.listing;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alexander on 4/13/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 2;
    private Context mContext;

    public PagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListingFragment.newInstance(position);
            case 1:
                return ListingFragment.newInstance(position);
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
