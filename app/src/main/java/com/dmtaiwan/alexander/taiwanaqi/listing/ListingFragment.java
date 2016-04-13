package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.utilities.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingFragment extends Fragment implements ListingAdapter.RecyclerClickListener, PagerAdapter.FragmentCallback {


    private int mPageNumber;
    private ListingAdapter mAdapter;
    private Callback mCallback;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    @Bind(R.id.aq_recycler_view)
    RecyclerView mRecyclerView;


    public static ListingFragment newInstance(int pageNumber) {
        ListingFragment listingFragment = new ListingFragment();
        listingFragment.mPageNumber = pageNumber;
        return listingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        ButterKnife.bind(this, rootView);
        setupAdapter();
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("FRAG", "Page number: " + mPageNumber);
        if (mPageNumber == 0) {
            mCallback.onFragmentReady();
        }
    }

    private void setupAdapter() {
        mAdapter = new ListingAdapter(getActivity(), mEmptyView, this, mPageNumber);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRecyclerClick(AQStation aqStation, List<AQStation> aqStationList) {

    }

    public void setData(List<AQStation> aqStations) {
        mAdapter.updateData(aqStations);
    }


    @Override
    public void updateFragment(List<AQStation> stations) {
        mAdapter.updateData(stations);
    }

    public interface Callback {
        public void onFragmentReady();
    }
}
