package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander on 4/13/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<AQStation> aqStations;
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return getTabTitle(mContext, position);
            case 1:
                return getTabTitle(mContext, position);
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        ListingFragment fragment = (ListingFragment) object;
        if (fragment != null) {
            fragment.updateFragment(aqStations);
        }
        return super.getItemPosition(object);
    }

    public static String getTabTitle(Context context, int tab) {
        String languagePref = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_language), context.getString(R.string.pref_language_eng));
        switch (tab) {
            case 0:
                if (languagePref.equals(context.getString(R.string.pref_language_zh))) {
                    return PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_county), context.getString(R.string.pref_county_taipei_city));
                } else if (languagePref.equals(context.getString(R.string.pref_language_eng))) {
                    String[] keyArray = context.getResources().getStringArray(R.array.pref_county_values);
                    List<String> keyArrayList = Arrays.asList(keyArray);
                    if (keyArrayList.contains(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_county), context.getString(R.string.pref_county_taipei_city)))) {
                        int number = keyArrayList.indexOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_county), context.getString(R.string.pref_county_taipei_city)));
                        String[] labelsArray = context.getResources().getStringArray(R.array.pref_county_options);
                        List<String> labelArray = Arrays.asList(labelsArray);
                        return labelArray.get(number);
                    } else return null;
                }
            case 1:
                if (languagePref.equals(context.getString(R.string.pref_language_zh))) {
                    return PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_secondary_county), context.getString(R.string.pref_county_taipei_city));
                } else if (languagePref.equals(context.getString(R.string.pref_language_eng))) {
                    String[] keyArray = context.getResources().getStringArray(R.array.pref_county_values);
                    List<String> keyArrayList = Arrays.asList(keyArray);
                    if (keyArrayList.contains(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_secondary_county), context.getString(R.string.pref_county_taipei_city)))) {
                        int number = keyArrayList.indexOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_secondary_county), context.getString(R.string.pref_county_taipei_city)));
                        String[] labelsArray = context.getResources().getStringArray(R.array.pref_county_options);
                        List<String> labelArray = Arrays.asList(labelsArray);
                        return labelArray.get(number);
                    } else return null;
                }
            default:
                return null;
        }
    }

    public void updateData(List<AQStation> stations) {
        aqStations = stations;
    }

    public interface FragmentCallback {
        void updateFragment(List<AQStation> stations);
    }
}
