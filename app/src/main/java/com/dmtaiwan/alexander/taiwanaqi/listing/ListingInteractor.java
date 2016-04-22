package com.dmtaiwan.alexander.taiwanaqi.listing;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.dmtaiwan.alexander.taiwanaqi.database.AqStationContract;
import com.dmtaiwan.alexander.taiwanaqi.network.HttpClientFactory;
import com.dmtaiwan.alexander.taiwanaqi.network.RequestGenerator;
import com.dmtaiwan.alexander.taiwanaqi.utilities.AqStationParser;
import com.dmtaiwan.alexander.taiwanaqi.utilities.Utilities;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.Vector;

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
    public Observable<Void> getNetworkData() {
        return Observable.defer(() -> {

            try {
                return Observable.just(getNetwork(context));
            } catch (Exception e) {
                return Observable.error(e);
            }

        });

    }

    private Void getNetwork(Context context) throws JSONException, IOException {
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
            Vector<ContentValues> contentValuesVector = AqStationParser.parse(json);
            if (contentValuesVector.size() > 0) {
                writeToDatabase(contentValuesVector);
            }
            return null;
        } else {
            Log.i("HTTP ERROR CODE", "CODE : " + response.code());
            throw new RuntimeException("Error: " + response.code());
        }
    }

    private void writeToDatabase(Vector<ContentValues> contentValuesVector) {
        context.getContentResolver().delete(AqStationContract.CONTENT_URI, null, null);
        ContentValues[] contentValuesArray = new ContentValues[contentValuesVector.size()];
        contentValuesVector.toArray(contentValuesArray);
        context.getContentResolver().bulkInsert(AqStationContract.CONTENT_URI, contentValuesArray);
    }
}
