package com.parkingsystem.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void show(Context context, String tag) {
        Toast.makeText(context, tag, Toast.LENGTH_SHORT).show();
    }
}
