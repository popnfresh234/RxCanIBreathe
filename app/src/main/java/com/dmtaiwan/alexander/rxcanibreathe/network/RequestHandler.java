package com.dmtaiwan.alexander.rxcanibreathe.network;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Alexander on 4/13/2016.
 */
public class RequestHandler {
    public static String request(Request request) throws IOException {
        Log.i("HTTP", request.method() + " : " + request.urlString());
        OkHttpClient httpClient= HttpClientFactory.getClient();
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        Log.i("HTTP", response.code() + " : " + body);

        if (response.isSuccessful()) {
            return body;
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
