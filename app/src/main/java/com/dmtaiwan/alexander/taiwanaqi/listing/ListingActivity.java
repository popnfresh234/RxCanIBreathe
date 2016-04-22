package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.settings.SettingsActivity;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;

public class ListingActivity extends AppCompatActivity implements IListingView, LayoutController{

    public static final String LOG_TAG = ListingActivity.class.getSimpleName();

    private PagerAdapter mPagerAdapter;
    private ListingPresenter mListingPresenter;
    private Subscription mSubscription;

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
            mSubscription = mListingPresenter.fetchData();
        }


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
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void enableScroll() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        params.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        );
        mToolbar.setLayoutParams(params);
    }

    @Override
    public void disableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        params.setScrollFlags(0);
        mToolbar.setLayoutParams(params);
    }

    @Override
    public void expandToolbar() {
        mAppBar.setExpanded(true);
    }
}
