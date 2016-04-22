package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.util.Log;

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
    public Subscription fetchData() {
        return mListingInteractor.getNetworkData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("NETWORK FAILED", "NETWORK FAILED");

                        mListingView.networkFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(Void avoid) {
                        mListingView.onSuccess(avoid);
                    }
                });
    }
}
