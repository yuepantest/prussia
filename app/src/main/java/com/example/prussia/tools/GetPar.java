package com.example.prussia.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.prussia.common.manage.MyApplication;

/**
 * author：Prussia
 * date：2018/9/5
 * describe：获取系统参数
 */

public class GetPar {

    /**
     * 获取手机屏幕像素密度（检查屏幕的分辨率属于高分辨率还是低分辨率）
     */
    public static String getPhoneMetric(Context context) {
        try {
            DisplayMetrics metric = context.getResources().getDisplayMetrics();
            int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240,小米4的是480）
            if (densityDpi <= 320) {
                return "1";
            } else if (densityDpi <= 480 && densityDpi > 320) {
                return "2";
            } else if (densityDpi <= 640 && densityDpi > 480) {
                return "3";
            } else {
                return "3";
            }
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getPhoneW() {
        WindowManager mWindowManager = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        // 更新浮动窗口位置参数 靠边
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getPhoneH() {
        WindowManager mWindowManager = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        // 更新浮动窗口位置参数 靠边
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取本地软件版本号
     */
    public static String getLocalVersion(Context context) {
        int localVersion = 0;
        try {
            if (context != null) {
                PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
                localVersion = packageInfo.versionCode;
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion + "";
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context context) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 半角转为全角
     *
     * @param input
     * @return
     */
    public static String getToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

}
