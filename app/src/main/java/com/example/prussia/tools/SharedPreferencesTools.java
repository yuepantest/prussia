package com.example.prussia.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prussia.common.manage.Constant;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by QD on 2017/8/4.
 */

public class SharedPreferencesTools {
    //存储信息
    public static void saveSP(Context context, String name, HashMap<String, String> map) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            editor.putString((String) entry.getKey(), (String) entry.getValue());
        }
        editor.commit();//把数据提交到文件里，在这之前数据都是存放在内存中
    }

    //获取存储信息
    public static String getSP(Context context, String name, String key) {
        if (context == null || name == null || key == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    //存储单个用户信息
    public static void saveSignUserSP(Context context, String name, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();//把数据提交到文件里，在这之前数据都是存放在内存中
    }

    //存储单个公共信息
    public static void saveSignSP(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.SpCode.SP_PUBLIC, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();//把数据提交到文件里，在这之前数据都是存放在内存中
    }

    //获取单个公共信息
    public static String getSignSP(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SpCode.SP_PUBLIC, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    //清空某个偏好设置的数据
    public static void setClearSP(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }
}
