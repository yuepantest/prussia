package com.example.prussia.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_SHORT_TIME = 500;
    private static long lastshortClickTime;

    public static boolean isFastShortClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastshortClickTime) >= MIN_CLICK_DELAY_SHORT_TIME) {
            flag = true;
        }
        lastshortClickTime = curClickTime;
        return flag;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    // 两次点击按钮之间的点击间隔不能少于5秒
    private static final int MIN_CLICK_DELAY_LONG_TIME = 5000;
    private static long lastLongClickTime;

    public static boolean isFastLongClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastLongClickTime) >= MIN_CLICK_DELAY_LONG_TIME) {
            flag = true;
        }
        lastLongClickTime = curClickTime;
        return flag;
    }


    /**
     * 判断是否有网络
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断手机是否安装QQ
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是是否有录音权限
     */
    public static boolean isVoicePermission(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO));
    }

    /**
     * 判断是否开启了相机权限
     */
    public static boolean isCameraPermission(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(context, android.Manifest.permission.CAMERA));
    }

    /**
     * 判断是否有存储空间权限
     */
    public static boolean isStoragePermission(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    /**
     * 判断是否有定位权限
     */
    public static boolean isPositioningPermission(Context context) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION));
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
