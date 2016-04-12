package com.dmtaiwan.alexander.taiwanaqi.listing;

import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;
import com.dmtaiwan.alexander.taiwanaqi.network.RequestGenerator;
import com.dmtaiwan.alexander.taiwanaqi.network.RequestHandler;
import com.dmtaiwan.alexander.taiwanaqi.utilities.AqStationParser;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by Alexander on 4/12/2016.
 */
public class ListingInteractor implements IListingInteractor{


    @Override
    public Observable<List<AQStation>> fetchStations() {
        return Observable.defer(new Func0<Observable<List<AQStation>>>() {
            @Override
            public Observable<List<AQStation>> call() {
                try {
                    return Observable.just(get());
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }

            private List<AQStation> get() throws IOException, JSONException {
                return fetch(Utilities.API_URL);
            }

            private List<AQStation> fetch(String apiUrl) throws IOException, JSONException {
                Request request = RequestGenerator.get(apiUrl);
                String response = RequestHandler.request(request);
                return AqStationParser.parse(response);
            }
        });
    }
}
