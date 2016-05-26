package com.dmtaiwan.alexander.taiwanaqi.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.dmtaiwan.alexander.taiwanaqi.R;
import com.dmtaiwan.alexander.taiwanaqi.models.AQStation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Alexander on 4/12/2016.
 */
public class Utilities {

    public static final String API_URL = "http://opendata.epa.gov.tw/ws/Data/AQX/?$orderby=County&$skip=0&$top=1000&format=json";
    public static final String FILE_NAME = "epa.json";
    public static final String DATABASE_NAME = "aq_database";

    //Intent extras
    public static final String EXTRA_AQ_STATION = "com.dmtaiwan.alexander.canibreathe.aqstation";
    public static final String EXTRA_AQ_STATIONS_LIST = "com.dmtaiwan.alexander.canibreathe.aqstationslist";

    public static final int RESULT_SETTING_CHANGED = 888;

    static public boolean doesFileExist(Context context) {
        File file = context.getFileStreamPath(FILE_NAME);
        return file.exists();
    }

    public static void writeToFile(String json, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readFromFile(Context context) {
        String json = "";
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                json = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static int getTextColor(String aqi, Context context) {
        if (aqi.equals("?")) {
            return context.getResources().getColor(R.color.textWhite);
        }
        double aqiDouble = Double.valueOf(aqi);
        if (aqiDouble > 51 && aqiDouble <= 100) {
            return context.getResources().getColor(R.color.textDarkBg54);
        } else return context.getResources().getColor(R.color.textWhite);
    }

    public static Drawable getAqiBackground(String aqi, Context context) {
        if (aqi.equals("?")) {
            return ContextCompat.getDrawable(context, R.drawable.circle_bg_gray);
        }

        double aqiDouble = Double.valueOf(aqi);

        if (aqiDouble <= 50) {
            return ContextCompat.getDrawable(context, R.drawable.circle_bg_green);
        } else if (aqiDouble >= 51 && aqiDouble <= 100) {
            return ContextCompat.getDrawable(context, R.drawable.circle_bg_yellow);
        } else if (aqiDouble >= 101 && aqiDouble <= 150) {
            return ContextCompat.getDrawable(context, R.drawable.circle_bg_orange);
        } else if (aqiDouble >= 151 && aqiDouble <= 200) {
            return ContextCompat.getDrawable(context, R.drawable.circle_bg_red);
        } else return ContextCompat.getDrawable(context, R.drawable.circle_bg_purple);
    }

    public static String getAQDetailTitle(int position, Context context) {
        switch (position) {
            case 0:
                return "Station";
            case 1:
                return context.getResources().getString(R.string.aq_detail_title_pm25);
            case 2:
                return context.getResources().getString(R.string.aq_detail_title_wind_direction);
            case 3:
                return context.getResources().getString(R.string.aq_detail_title_wind_speed);
            case 4:
                return context.getResources().getString(R.string.aq_detail_title_update);
            default:
                return null;
        }
    }

    public static int getAqIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.icon_station;
            case 1:
                return R.drawable.icon_pm25;
            case 2:
                return R.drawable.icon_wind_direction;
            case 3:
                return R.drawable.icon_wind_speed;
            case 4:
                return R.drawable.icon_time;
            default:
                return 0;
        }
    }

    public static String getAqData(int position, AQStation aqStation) {
        switch (position) {
            case 0:
                return aqStation.getSiteName();
            case 1:
                return aqStation.getAQI();
            case 2:
                return formatWindDirection(aqStation.getWindDirec());
            case 3:
                return aqStation.getFormattedWindSpeed();
            case 4:
                return aqStation.getFormattedTime();
            default:
                return null;
        }
    }

    public static String formatWindDirection(String windDirection) {
        if (windDirection.equals("")) {
            return "0" + "\u00B0";
        } else {
            return windDirection + "\u00B0";
        }
    }

    public static float getWindDegreeForRotate(String windDirection) {
        if (windDirection.equals("")) {
            return 0;
        } else {
            return Float.valueOf(windDirection);
        }
    }



    public static int getDetailHeader(String aqi, Context context) {
        if (aqi.equals("?")) {
            return R.drawable.aq_good;
        }

        double aqiDouble = Double.valueOf(aqi);

        if (aqiDouble <= 50) {
            return R.drawable.aq_good;
        } else if (aqiDouble > 51 && aqiDouble <= 100) {
            return R.drawable.aq_moderate;
        } else if (aqiDouble > 101 && aqiDouble <= 150) {
            return R.drawable.aq_unhealthy;
        } else if (aqiDouble > 151 && aqiDouble <= 200) {
            return R.drawable.aq_dangerous;
        } else return R.drawable.aq_unknown;
    }
}
