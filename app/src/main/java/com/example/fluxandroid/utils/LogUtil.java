package com.example.fluxandroid.utils;

import android.util.Log;

/**
 * @author hjx
 * @date 10/9/2015
 * @time 17:29
 * @description
 */
public class LogUtil {

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
