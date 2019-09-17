package com.example.prussia.tools;


/**
 * Created by QD on 2017/7/19.
 */

public class LogUtil {
    private final static String defaultTag = "TAG";

    public static void I(String msg) {
        android.util.Log.i(defaultTag, msg);
    }

    public static void E(String msg) {
        android.util.Log.e(defaultTag, msg);
    }
}


