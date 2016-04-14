package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;
import android.util.Log;

import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.models.RxResponse;
import com.dmtaiwan.alexander.taiwanaqi.network.HttpClientFactory;
import com.dmtaiwan.alexander.taiwanaqi.network.RequestGenerator;
import com.dmtaiwan.alexander.taiwanaqi.utilities.AqStationParser;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import rx.Observable;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingInteractor implements IListingInteractor {

    private Context context;


    public ListingInteractor(Context context) {
        this.context = context;
    }

    @Override
    public Observable<RxResponse> getNetworkData() {
        return Observable.defer(() -> {

            try {
                return Observable.just(getNetwork(context));
            } catch (Exception e) {
                return Observable.error(e);
            }

        });

    }

    @Override
    public Observable<RxResponse> getCacheData() {
        return Observable.defer(() -> {
            try {
                return Observable.just(getDisk(context));
            } catch (Exception e) {
                return Observable.error(e);
            }
        });
    }

    private RxResponse getDisk(Context context) throws JSONException {
        if (Utilities.doesFileExist(context)) {
            String json = Utilities.readFromFile(context);
            List<AQStation> stations = AqStationParser.parse(json);
            return new RxResponse(RxResponse.CACHE_CALL, stations);
        } else throw new RuntimeException("No local cache data available");
    }

    private RxResponse getNetwork(Context context) throws JSONException, IOException {
        Request request = RequestGenerator.get(Utilities.API_URL);
        OkHttpClient client = HttpClientFactory.getClient();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("Could not complete network request");
        }
        if (response.isSuccessful()) {
            String json = null;
            try {
                json = response.body().string();
            } catch (IOException e) {
                throw e;
            }
            Utilities.writeToFile(json, context);
            List<AQStation> stations = AqStationParser.parse(json);
            return new RxResponse(RxResponse.NETWORK_CALL, stations);
        } else {
            Log.i("HTTP ERROR CODE", "CODE : " + response.code());
            throw new RuntimeException("Error: " + response.code());
        }
    }
}
