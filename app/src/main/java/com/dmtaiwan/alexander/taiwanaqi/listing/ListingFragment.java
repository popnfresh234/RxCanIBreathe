package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.DividerItemDecoration;
import com.dmtaiwan.alexander.taiwanaqi.utilities.RecyclerViewCustom;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingFragment extends Fragment implements ListingAdapter.RecyclerClickListener{



    private int mPageNumber;
    private ListingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<AQStation> mAqStations;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Bind(R.id.aq_recycler_view)
    RecyclerViewCustom mRecyclerView;



    public static ListingFragment newInstance(int pageNumber, List<AQStation> aqStations) {
        ListingFragment listingFragment = new ListingFragment();
        listingFragment.mPageNumber = pageNumber;
        listingFragment.mAqStations = aqStations;
        return listingFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Frag OnResume", "onResume");
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

    private void setupRecyclerView() {
        mAdapter = new ListingAdapter(getActivity(), mEmptyView, this, mPageNumber);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateData(mAqStations);
        mRecyclerView.addOnGlobalLayoutListener();
    }

    @Override
    public void onRecyclerClick(AQStation aqStation, List<AQStation> aqStationList) {

    }
}
