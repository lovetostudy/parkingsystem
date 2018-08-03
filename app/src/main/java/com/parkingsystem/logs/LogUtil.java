package com.parkingsystem.logs;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class LogUtil {

    private static final String TAG = "LogUtil";

    public static void logRespnse(String responseString) {
        Log.i(TAG, responseString);
    }

    public static void logErr(String errorString) {
        Log.e(TAG, errorString );
    }

    public static void logRequest(String requestString) {
        Log.i(TAG, requestString);
    }


    public static void logList(String list) {
        Log.i(TAG, list);

    }

    public static void logArrayList(String logArrayList) {

        Log.i(TAG, logArrayList);
    }
}
