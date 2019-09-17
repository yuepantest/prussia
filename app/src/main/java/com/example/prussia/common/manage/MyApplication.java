package com.example.prussia.common.manage;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2016/9/huise 0001.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        MultiDex.install(mContext);//初始化分包机制
    }

    public static Context getContext() {
        return mContext;
    }

}


