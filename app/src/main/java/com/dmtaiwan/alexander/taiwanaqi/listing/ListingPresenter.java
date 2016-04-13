package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;

import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingPresenter implements IListingPresenter {

    private IListingView mListingView;
    private IListingInteractor mListingInteractor;

    public ListingPresenter(IListingView view, Context context) {
        mListingView = view;
        mListingInteractor = new ListingInteractor(context);
    }
    @Override
    public Subscription displayStations() {
        return mListingInteractor.fetchStations().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mListingView.loadingStarted();
                    }
                }).subscribe(new Subscriber<List<AQStation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mListingView.loadingFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AQStation> stations) {
                        mListingView.showStations(stations);
                    }
                });
    }
}
