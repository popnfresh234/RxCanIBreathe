package com.dmtaiwan.alexander.rxcanibreathe.listing;

import com.dmtaiwan.alexander.rxcanibreathe.models.AQStation;

import java.util.List;

/**
 * Created by Alexander on 4/12/2016.
 */
public interface IListingView {
    void showStations(List<AQStation> stations);

    void loadingStarted();

    void loadingFailed(String errorMessage);

    void onStationClicked(AQStation aqStation);
}
