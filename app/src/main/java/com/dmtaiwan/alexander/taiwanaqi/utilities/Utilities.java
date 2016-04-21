package com.dmtaiwan.alexander.taiwanaqi.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.dmtaiwan.alexander.taiwanaqi.R;

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


}
