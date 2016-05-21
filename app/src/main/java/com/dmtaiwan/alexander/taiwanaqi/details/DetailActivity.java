package com.dmtaiwan.alexander.taiwanaqi.details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 11/13/2015.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private List<AQStation> mAQStationsSorted;
    private AQStation mCurrentAQStation;
    private FragmentPagerAdapter mPagerAdapter;




    @Bind(R.id.view_pager)
    ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            mAQStationsSorted = getIntent().getParcelableArrayListExtra(Utilities.EXTRA_AQ_STATIONS_LIST);
            mCurrentAQStation = getIntent().getParcelableExtra(Utilities.EXTRA_AQ_STATION);
        }
        Log.i("Test", String.valueOf(mAQStationsSorted.size()));
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this, mAQStationsSorted);
        mViewPager.setAdapter(mPagerAdapter);
        for (int i = 0; i < mAQStationsSorted.size(); i++) {
            AQStation station = mAQStationsSorted.get(i);
            if (station.getSiteNumber() == mCurrentAQStation.getSiteNumber()) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private List<AQStation> mAQStationList;

        public PagerAdapter(FragmentManager fragmentManager, Context context, List<AQStation> aqStationList) {
            super(fragmentManager);
            mContext = context;
            mAQStationList = aqStationList;
        }

        @Override
        public Fragment getItem(int position) {
            AQStation station = mAQStationList.get(position);
            Fragment frag = DetailFragment.newInstance(position, station.getSiteName());
            Bundle args = new Bundle();
            args.putParcelable(Utilities.EXTRA_AQ_STATION, mAQStationList.get(position));
            frag.setArguments(args);
            return frag;
        }

        @Override
        public int getCount() {
            return mAQStationList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mAQStationList.get(position).getSiteName();
        }

    }
}
