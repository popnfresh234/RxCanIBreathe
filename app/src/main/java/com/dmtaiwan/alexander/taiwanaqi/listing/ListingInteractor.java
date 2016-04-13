package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.Context;

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
public class ListingInteractor implements IListingInteractor {

    private Context context;

    public ListingInteractor(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<AQStation>> fetchStations() {
        return Observable.defer(new Func0<Observable<List<AQStation>>>() {
            @Override
            public Observable<List<AQStation>> call() {
                try {
                    return Observable.just(getNewtork());
                } catch (Exception e) {
                    return handleException(e);
                }
            }
        });
    }

    private Observable<List<AQStation>> handleException(Exception e) {
        if (Utilities.doesFileExist(context)) {
            try {
                return Observable.just(getCache());
            } catch (JSONException e1) {
                return Observable.error(e1);
            }
        } else {
            return Observable.error(e);
        }
    }

    private List<AQStation> getNewtork() throws IOException, JSONException {
        return fetch(Utilities.API_URL);
    }

    private List<AQStation> getCache() throws JSONException {
        return AqStationParser.parse(Utilities.readFromFile(context));
    }

    private List<AQStation> fetch(String apiUrl) throws IOException, JSONException {
        Request request = RequestGenerator.get(apiUrl);
        String response = RequestHandler.request(request, context);
        return AqStationParser.parse(response);
    }
}
