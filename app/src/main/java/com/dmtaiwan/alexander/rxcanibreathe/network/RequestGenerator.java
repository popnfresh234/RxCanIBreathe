package com.dmtaiwan.alexander.rxcanibreathe.network;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Request;

/**
 * Created by Alexander on 4/13/2016.
 */
public class RequestGenerator {
    /**
     * Adds default header for all requests
     *
     * @param builder
     */
    private static void addDefaultHeaders(@NonNull Request.Builder builder) {
        builder.header("Accept", "application/json");
    }


    /**
     * Builds a get Request object
     *
     * @param url
     */
    public static Request get(@NonNull String url) {
        Request.Builder builder = new Request.Builder().url(url);
        addDefaultHeaders(builder);
        return builder.build();
    }
}
