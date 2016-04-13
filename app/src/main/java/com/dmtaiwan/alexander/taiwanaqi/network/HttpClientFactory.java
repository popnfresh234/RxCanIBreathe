package com.dmtaiwan.alexander.taiwanaqi.network;

import com.squareup.okhttp.OkHttpClient;

import net.jcip.annotations.GuardedBy;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander on 4/13/2016.
 */
public class HttpClientFactory {
    private static final Object LOCK = new Object();
    private static final int TIMEOUT_IN_MS = 10000;

    @GuardedBy("LOCK")
    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getClient() {
        synchronized (LOCK) {
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient();
                mOkHttpClient.setConnectTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                mOkHttpClient.setReadTimeout(TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
                setCookieHandler();
            }
        }
        return mOkHttpClient;
    }

    private static void setCookieHandler() {
        synchronized (LOCK) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
            mOkHttpClient.setCookieHandler(cookieManager);
        }
    }
}
