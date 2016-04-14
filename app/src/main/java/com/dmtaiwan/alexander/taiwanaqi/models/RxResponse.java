package com.dmtaiwan.alexander.taiwanaqi.models;

import java.util.List;

/**
 * Created by Alexander on 4/14/2016.
 */
public class RxResponse {
    public static final int NETWORK_CALL = 0;
    public static final int CACHE_CALL = 1;

    private List<AQStation> aqStations;
    private int responseType;

    public RxResponse(int responseType, List<AQStation> aqStations) {
        this.aqStations = aqStations;
        this.responseType = responseType;
    }

    public List<AQStation> getAqStations() {
        return aqStations;
    }

    public void setAqStations(List<AQStation> aqStations) {
        this.aqStations = aqStations;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }


}
