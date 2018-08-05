package com.parkingsystem.logs;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class LogUtil {

    private static final String TAG = "LogUtil";

    public static void logRespnse(String responseString) {
        Log.i(TAG, "logRespnse : " + responseString);
    }

    public static void logErr(String errorString) {
        Log.e(TAG, "logErr : " + errorString );
    }

    public static void logRequest(String requestString) {
        Log.i(TAG, "logRequest : " + requestString);
    }


    public static void logList(String list) {
        Log.i(TAG, "logList : " + list);

    }

    public static void logArrayList(String logArrayList) {

        Log.i(TAG, "logArrayList : " + logArrayList);
    }

    public static void logArrayListInfo(String des, String user_name) {
        Log.i(TAG, des + " : " + user_name);
    }

    public static void logQueryUserInfo(String s) {
        Log.i(TAG, s);
    }
}
