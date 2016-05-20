package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.database.AqStationContract;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.settings.SettingsActivity;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class ListingActivity extends AppCompatActivity implements IListingView, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = ListingActivity.class.getSimpleName();
    private static final int LOADER_ID = 888;

    private PagerAdapter mPagerAdapter;
    private ListingPresenter mListingPresenter;
    private Subscription mSubscription;
    private List<AQStation> mAqStations;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.appbar)
    AppBarLayout mAppBar;

    @Bind(R.id.toolbar_progress)
    ProgressBar mProgressBar;

    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setupViewPager();
        mListingPresenter = new ListingPresenter(this, getApplicationContext());
        if (savedInstanceState == null) {
//            mSubscription = mListingPresenter.fetchData();
        }
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void setupViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("SELECTED", "SELECTED: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 111);
            return true;
        }

        if (id == R.id.action_refresh) {
            mSubscription = mListingPresenter.fetchData();
        }

        return super.onOptionsItemSelected(item);
    }


    private void updateTabs() {
        TabLayout.Tab tab0 = mTabLayout.getTabAt(0);
        TabLayout.Tab tab1 = mTabLayout.getTabAt(1);
        tab0.setText(mPagerAdapter.getTabTitle(this, 0));
        tab1.setText(mPagerAdapter.getTabTitle(this, 1));
    }

    @Override
    public void onSuccess(Void avoid) {
        makeSnackBar("Update Successful!");
    }



    @Override
    public void loadingStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void networkFailed(String error) {
        makeSnackBar(error);
    }


    private void makeSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Utilities.RESULT_SETTING_CHANGED) {
            updateTabs();
            mPagerAdapter.updateData(mAqStations);
        }
    }



    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, AqStationContract.CONTENT_URI, AqStationContract.STATION_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        data.moveToFirst();
        Log.i("onLoadFinished", "Size: " + data.getCount());
        List<AQStation> aqStations = new ArrayList<>();
        while (data.moveToNext()) {
            //Create list of AqStations to populate adapter
            AQStation aqStation = new AQStation();
            aqStation.setSiteNumber(data.getInt(AqStationContract.STATION_ID_INT));
            aqStation.setSiteName(data.getString(AqStationContract.SITE_NAME_INT));
            aqStation.setCounty(data.getString(AqStationContract.COUNTY_INT));
            aqStation.setPM25(data.getString(AqStationContract.PM25_INT));
            aqStation.setAQI(data.getString(AqStationContract.AQI_INT));
            aqStation.setWindSpeed(data.getString(AqStationContract.WIND_SPEED_INT));
            aqStation.setFormattedWindSpeed(data.getString(AqStationContract.FORMATTED_WIND_SPEED_INT));
            aqStation.setWindDirec(data.getString(AqStationContract.WIND_DIRECTION_INT));
            aqStation.setPublishTime(data.getString(AqStationContract.PUBLISH_TIME_INT));
            aqStation.setFormattedTime(data.getString(AqStationContract.FORMATTED_TIME_INT));
            aqStations.add(aqStation);
        }
        Log.i(LOG_TAG, "Cursor Loaded " + aqStations.size() + " stations");
        mAqStations = aqStations;
        mPagerAdapter.updateData(mAqStations);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void restartLoader() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }


}
