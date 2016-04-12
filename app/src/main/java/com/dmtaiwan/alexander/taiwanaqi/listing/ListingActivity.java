package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.settings.SettingsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class ListingActivity extends AppCompatActivity implements IListingView{

    public static final String LOG_TAG = ListingActivity.class.getSimpleName();
    public static final String LISTING_PRIMARY = "ListingPrimary";
    public static final String LISTING_SECONDARY = "ListingSecondary";

    private PagerAdapter mPagerAdapter;
    private ListingPresenter mListingPresenter;
    private Subscription mStationsSubscription;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

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
        mListingPresenter = new ListingPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStationsSubscription = mListingPresenter.displayStations();
    }

    @Override
    protected void onDestroy() {
        if (mStationsSubscription != null && !mStationsSubscription.isUnsubscribed()) {
            mStationsSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void setupViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
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
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showStations(List<AQStation> stations) {
        mProgressBar.setVisibility(View.INVISIBLE);
        ListingFragment primaryFragment = (ListingFragment) mPagerAdapter.instantiateItem(mViewPager, 0);
        ListingFragment secondaryFragment = (ListingFragment) mPagerAdapter.instantiateItem(mViewPager, 1);
        primaryFragment.setData(stations);
        secondaryFragment.setData(stations);
        updateTabs();
    }

    private void updateTabs() {
        TabLayout.Tab tab0 = mTabLayout.getTabAt(0);
        TabLayout.Tab tab1 = mTabLayout.getTabAt(1);
        tab0.setText(mPagerAdapter.getTabTitle(this, 0));
        tab1.setText(mPagerAdapter.getTabTitle(this, 1));
    }

    @Override
    public void loadingStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingFailed(String errorMessage) {
        mProgressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(mCoordinatorLayout, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStationClicked(AQStation aqStation) {

    }
}
