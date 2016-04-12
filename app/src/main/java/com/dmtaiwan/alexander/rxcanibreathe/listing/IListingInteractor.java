package com.dmtaiwan.alexander.rxcanibreathe.listing;

import com.dmtaiwan.alexander.rxcanibreathe.models.AQStation;

import java.util.List;

import rx.Observable;

/**
 * Created by Alexander on 4/12/2016.
 */
public interface IListingInteractor {
    Observable<List<AQStation>> fetchStations();
}
