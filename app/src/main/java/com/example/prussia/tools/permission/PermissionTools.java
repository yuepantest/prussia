package com.example.prussia.tools.permission;

import android.Manifest;
import android.app.Activity;

/**
 * author：Prussia
 * date：2018/5/11
 * describe：
 */

public class PermissionTools {

    //提示用户开启权限
    public static void permission(String type, Activity activity) {
        if (type.equals("麦克权限")) {
            DMPermission.with(activity)
                    .setNeedPermissionMsg("喵喵玩玩需要麦克风权限才能正常工作")
                    .setDeniedPermissionMsg("请开启麦克风权限")
                    .setPermissionAndRequest(
                            Manifest.permission.RECORD_AUDIO)
                    .setListener(new DMPermission.OnListener() {
                        @Override
                        public void onFail() {

                        }

                        @Override
                        public void onSuccess() {

                        }
                    });
        } else if (type.equals("存储权限")) {
            DMPermission.with(activity)
                    .setNeedPermissionMsg("喵喵玩需要外部储存权限才能正常工作")
                    .setDeniedPermissionMsg("喵喵玩需要到应用管理确保设置了外部储存权限")
                    .setPermissionAndRequest(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setListener(new DMPermission.OnListener() {
                        @Override
                        public void onFail() {

                        }

                        @Override
                        public void onSuccess() {

                        }
                    });
        }
    }
}
