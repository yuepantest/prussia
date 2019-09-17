package com.example.prussia.tools.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DMing on 2017/7/4.
 */

public class DMPermission {

    static String TAG = "DMUI";
    private boolean stopSetting = false;
    private String[] deniedPerList;
    static final int REQUEST_CODE_REQUEST_PERMISSION = 0x0001;
    private static final int REQUEST_CODE_REQUEST_SETTING = 0x0002;
    private String needPermissionMsg = "";
    private String deniedPermissionMsg = "";
    private static WeakReference<Activity> activityWeakReference;
    private static WeakReference<DMPermission> dmPermissionWeakReference;
    private OnListener onListener;
    private String[] mPermissionSource;

    /**
     * 获取activity
     *
     * @param activity Activity
     * @return DMPermission
     */
    public static DMPermission with(@NonNull Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
        dmPermissionWeakReference = new WeakReference<>(new DMPermission());
        return dmPermissionWeakReference.get();
    }

    /**
     * 设置需要那些权限文字
     *
     * @param msg 需要什么权限说明
     * @return DMPermission
     */
    public DMPermission setNeedPermissionMsg(@NonNull String msg) {
        needPermissionMsg = msg;
        return this;
    }

    /**
     * 设置禁止权限会导致什么问题
     *
     * @param msg 设置说明内容
     * @return DMPermission
     */
    public DMPermission setDeniedPermissionMsg(@NonNull String msg) {
        deniedPermissionMsg = msg;
        return this;
    }

    /**
     * 设置所需要的权限
     *
     * @param mPermission 权限list
     * @return DMPermission
     */
    public DMPermission setPermissionAndRequest(@NonNull String... mPermission) {
        mPermissionSource = mPermission;
        return this;
    }

    /**
     * 监听是否获取权限都成功
     *
     * @param onListener {@link OnListener}
     */
    public void setListener(OnListener onListener) {
        this.onListener = onListener;
        start();
    }

    /**
     * 开始请求
     */
    private void start() {
        stopSetting = false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callbackSucceed();
        } else {
            if (activityWeakReference == null || activityWeakReference.get() == null) {
                List<String> deniedList = Arrays.asList(mPermissionSource);
                callbackFailed(deniedList);
            } else {
                String[] mDeniedPermissions = PermissionUtils.getDeniedPermissions(activityWeakReference.get(), mPermissionSource);//获取那些权限尚没有获取
                if (PermissionUtils.hasSelfPermissions(activityWeakReference.get(), mDeniedPermissions)) {
                    callbackSucceed();
                } else {
                    if (PermissionUtils.shouldShowRequestPermissionRationale(activityWeakReference.get(), mDeniedPermissions)) {
                        stopSetting = true;
                        showRationaleDialog(activityWeakReference.get(), mDeniedPermissions);
                    } else {
                        startPermissionActivity(activityWeakReference.get(), mDeniedPermissions);
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startPermissionActivity(Activity target, String[] mDeniedPermissions) {
        Intent intent = new Intent(target, PermissionActivity.class);
        intent.putExtra(PermissionActivity.KEY_INPUT_PERMISSIONS, mDeniedPermissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        target.startActivity(intent);
        target.overridePendingTransition(0, 0);
    }

    /**
     * 假如用户拒绝权限并点击了不再提示，这个时候只能到应用的设置管理手动把权限授权
     *
     * @param activity Activity
     */
    private void startSetting(Activity activity) {
        //跳转授权界面
        activity.finish();
        SettingsCompat.manageDrawOverlaysForRom(activity);
        //SettingsCompat.manageDrawOverlays(activity);
        /*if (MiuiOs.isMIUI()) { //单独对小米进行处理（具体没实测）
            Intent intent = MiuiOs.getSettingIntent(activity);
            if (MiuiOs.isIntentAvailable(activity, intent)) {
                activity.startActivityForResult(intent, REQUEST_CODE_REQUEST_SETTING);
                return;
            }
        }
        try { // 一般化的处理流程
            Intent intent = fastorder_new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) //到自己应用的设置页面
                    .setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_REQUEST_SETTING);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = fastorder_new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);//出错 到应用管理页面
                activity.startActivityForResult(intent, REQUEST_CODE_REQUEST_SETTING);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }*/
    }

    /**
     * 请求权限回调
     *
     * @param activity     Activity
     * @param permissions  权限列表
     * @param grantResults 同意
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onDMRequestPermissionsResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permissions[i]);
            }
        }
        if (deniedList.isEmpty()) {
            callbackSucceed();
            activity.finish();
        } else {
            deniedPerList = deniedList.toArray(new String[deniedList.size()]);
            if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, deniedPerList) && !stopSetting) {
                showSettingDialog(activity, deniedPerList);
            } else {
                callbackFailed(deniedList);
                activity.finish();
            }
        }
    }

    /**
     * 请求权限回调 {@link DMPermission#onDMRequestPermissionsResult}
     *
     * @param activity     Activity
     * @param permissions  权限列表
     * @param grantResults 同意
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    static void onRequestPermissionsResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (dmPermissionWeakReference != null && dmPermissionWeakReference.get() != null) {
            dmPermissionWeakReference.get().onDMRequestPermissionsResult(activity, permissions, grantResults);
        } else {
            activity.finish();
        }
    }

    /**
     * 请求setting的回调
     *
     * @param activity Activity
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    static void onActivityResult(Activity activity) {
        if (dmPermissionWeakReference != null && dmPermissionWeakReference.get() != null) {
            dmPermissionWeakReference.get().setPermissionAndRequest(dmPermissionWeakReference.get().deniedPerList);
        }
        activity.finish();
    }

    /**
     * 是否获取权限都成功监听
     */
    public interface OnListener {
        void onFail();

        void onSuccess();
    }

    /**
     * 失败回调
     *
     * @param deniedList 拒绝权限列表
     */
    private void callbackFailed(List<String> deniedList) {
        for (String str : deniedList) {
            Log.i(TAG, "callbackFailed: " + str);
        }
        if (onListener != null) onListener.onFail();
    }

    /**
     * 成功回调
     */
    private void callbackSucceed() {
        Log.i(TAG, "callbackSucceed");
        if (onListener != null) onListener.onSuccess();
    }

    /**
     * 需要什么权限的dialog
     *
     * @param activity     Activity
     * @param mPermissions 权限列表
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showRationaleDialog(final Activity activity, final String[] mPermissions) {
        new ShowDialogCancel(activity)
                .setOnClick(new ShowDialogCancel.OnClick() {
                    @Override
                    public void confirm() {
                        startPermissionActivity(activity, mPermissions);
                    }

                    @Override
                    public void cancel() {
                        List<String> deniedList = Arrays.asList(mPermissions);
                        callbackFailed(deniedList);
                    }
                })
                .show(needPermissionMsg);
    }

    /**
     * 用户拒绝权限并点击了不再提示，这个时候只能提示到到应用的设置管理手动把权限授权 dialog
     *
     * @param activity     Activity
     * @param mPermissions 权限列表
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showSettingDialog(final Activity activity, final String[] mPermissions) {
        new ShowDialogCancel(activity)
                .setOnClick(new ShowDialogCancel.OnClick() {
                    @Override
                    public void confirm() {
                        startSetting(activity);
                    }

                    @Override
                    public void cancel() {
                        List<String> deniedList = Arrays.asList(mPermissions);
                        callbackFailed(deniedList);
                        activity.finish();
                    }
                })
                .show(deniedPermissionMsg);
    }
}
