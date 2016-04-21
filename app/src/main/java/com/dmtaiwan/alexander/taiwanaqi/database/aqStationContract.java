package com.dmtaiwan.alexander.taiwanaqi.database;

import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.Column.Type;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by lenovo on 4/21/2016.
 */
public interface AqStationContract extends ProviGenBaseContract{



    @Column(Type.INTEGER)
    public static final String STATION_ID = "id";

    @Column(Type.TEXT)
    public static final String SITE_NAME = "site_name";

    @Column(Type.TEXT)
    public static final String COUNTY = "county";

//    @Column(Type.TEXT)
//    public static final String PSI = "psi";
//
//    @Column(Type.TEXT)
//    public static final String MAJOR_POLLUTANT = "major_pollutant";
//
//    @Column(Type.TEXT)
//    public static final String STATUS = "status";
//
//    @Column(Type.TEXT)
//    public static final String SO2 = "so2";
//
//    @Column(Type.TEXT)
//    public static final String O3 = "o3";
//
//    @Column(Type.TEXT)
//    public static final String PM10 = "pm10";

    @Column(Type.TEXT)
    public static final String PM25 = "pm25";

    @Column(Type.TEXT)
    public static final String AQI = "aqi";

//    @Column(Type.TEXT)
//    public static final String NO2 = "string";

    @Column(Type.TEXT)
    public static final String WIND_SPEED = "wind_speed";

    @Column(Type.TEXT)
    public static final String FORMATTED_WIND_SPEED = "formatted_wind_speed";

    @Column(Type.TEXT)
    public static final String WIND_DIRECTION = "wind_direction";

//    @Column(Type.TEXT)
//    public static final String FPMI = "fpmi";
//
//    @Column(Type.TEXT)
//    public static final String NOX = "nox";
//
//    @Column(Type.TEXT)
//    public static final String NO = "no";

    @Column(Type.TEXT)
    public static final String PUBLISH_TIME = "publish_time";

    @Column(Type.TEXT)
    public static final String FORMATTED_TIME = "formatted_time";

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse("content://com.dmtaiwan.alexander.rxcanibreathe/aq_stations");

    public static final String[] STATION_COLUMNS ={
            STATION_ID,
            SITE_NAME,
            COUNTY,
            PM25,
            AQI,
            WIND_SPEED,
            FORMATTED_WIND_SPEED,
            WIND_DIRECTION,
            PUBLISH_TIME,
            FORMATTED_TIME
    };

    public static final int STATION_ID_INT = 0;
    public static final int SITE_NAME_INT = 1;
    public static final int COUNTY_INT = 2;
    public static final int PM25_INT = 3;
    public static final int AQI_INT = 4;
    public static final int WIND_SPEED_INT = 5;
    public static final int FORMATTED_WIND_SPEED_INT = 6;
    public static final int WIND_DIRECTION_INT = 7;
    public static final int PUBLISH_TIME_INT = 8;
    public static final int FORMATTED_TIME_INT = 9;


}
