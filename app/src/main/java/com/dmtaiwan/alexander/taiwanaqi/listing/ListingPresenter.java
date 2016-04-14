package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.util.Log;

import com.dmtaiwan.alexander.taiwanaqi.models.RxResponse;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    public Subscription displayCacheData() {
        return mListingInteractor.getCacheData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RxResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("CACHE FAILED", "CACHE FAILED");
                        mListingView.cacheFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RxResponse rxResponse) {
                        mListingView.showStations(rxResponse);
                    }
                });
    }

    @Override
    public Subscription displayNetworkData() {
        return mListingInteractor.getNetworkData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RxResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("NETWORK FAILED", "NETWORK FAILED");

                        mListingView.networkFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(RxResponse rxResponse) {
                        mListingView.showStations(rxResponse);
                    }
                });
    }
}
