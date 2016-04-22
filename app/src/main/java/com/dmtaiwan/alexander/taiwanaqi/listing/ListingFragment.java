package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.database.AqStationContract;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingFragment extends Fragment implements ListingAdapter.RecyclerClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DOWN = 1;
    private static final int UP = 0;
    private final int delay = 50;

    private int mPageNumber;
    private ListingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private LayoutController mLayoutController;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Bind(R.id.aq_recycler_view)
    RecyclerView mRecyclerView;


    public static ListingFragment newInstance(int pageNumber) {
        ListingFragment listingFragment = new ListingFragment();
        listingFragment.mPageNumber = pageNumber;
        return listingFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mLayoutController = (ListingActivity) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setBehavior();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        ButterKnife.bind(this, rootView);
        setRetainInstance(true);
        setupRecyclerView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(mPageNumber, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupRecyclerView() {
        mAdapter = new ListingAdapter(getActivity(), mEmptyView, this, mPageNumber);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        //TODO behavior
        setBehavior();
    }

    @Override
    public void onRecyclerClick(AQStation aqStation, List<AQStation> aqStationList) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), AqStationContract.CONTENT_URI, AqStationContract.STATION_COLUMNS, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
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
        mAdapter.updateData(aqStations);
        //TODO check if scrollable, if so modify behavior
        setBehavior();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void restartLoader() {
        getActivity().getSupportLoaderManager().restartLoader(mPageNumber, null, this);
    }

    private void setBehavior() {
        if (mRecyclerView != null) {


        final LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //no items in the RecyclerView
                if (mRecyclerView.getAdapter().getItemCount() == 0) {
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mLayoutController.expandToolbar();
                }

                    //if the first and the last item is visible
                else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        && layoutManager.findLastCompletelyVisibleItemPosition() == mRecyclerView.getAdapter().getItemCount() - 1) {
                    mRecyclerView.setNestedScrollingEnabled(false);
                    mLayoutController.expandToolbar();
                }
                else
                    mRecyclerView.setNestedScrollingEnabled(true);
            }
        }, 50);
    }
    }
}
