package com.dmtaiwan.alexander.taiwanaqi.listing;

import com.dmtaiwan.alexander.taiwanaqi.models.RxResponse;

import rx.Observable;

/**
 * Created by Alexander on 4/12/2016.
 */
public interface IListingInteractor {
    Observable<RxResponse> getNetworkData();
}
