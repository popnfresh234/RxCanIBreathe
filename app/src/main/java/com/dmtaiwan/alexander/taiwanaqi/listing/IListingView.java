package com.dmtaiwan.alexander.taiwanaqi.listing;

import com.dmtaiwan.alexander.taiwanaqi.models.RxResponse;

/**
 * Created by Alexander on 4/12/2016.
 */
public interface IListingView {
    void showStations(RxResponse rxResponse);

    void loadingStarted();

    void networkFailed(String error);

    void cacheFailed(String error);
}
