package com.dmtaiwan.alexander.taiwanaqi.listing;

/**
 * Created by Alexander on 4/12/2016.
 */
public interface IListingView {
    void onSuccess(Void avoid);

    void loadingStarted();

    void networkFailed(String error);

}
