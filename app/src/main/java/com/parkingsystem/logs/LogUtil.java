package com.parkingsystem.logs;

import android.util.Log;

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
}
