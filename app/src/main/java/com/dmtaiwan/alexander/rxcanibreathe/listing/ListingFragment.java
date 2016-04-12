package com.dmtaiwan.alexander.rxcanibreathe.listing;

import android.support.v4.app.Fragment;

import com.dmtaiwan.alexander.rxcanibreathe.models.AQStation;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingFragment extends Fragment {

    private int mPageNumber;

    public static ListingFragment newInstance (int pageNumber) {
        ListingFragment listingFragment = new ListingFragment();
        listingFragment.mPageNumber = pageNumber;
        return listingFragment;
    }

    public interface Callback {
        void onStationsLoaded(AQStation aqStation);

        void onStationClicked(AQStation aqStation);
    }
}
