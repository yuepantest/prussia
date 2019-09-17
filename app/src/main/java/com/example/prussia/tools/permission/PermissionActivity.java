package com.example.prussia.tools.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static com.example.prussia.tools.permission.DMPermission.REQUEST_CODE_REQUEST_PERMISSION;

/**
 * Created by DMing on 2017/7/4.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class PermissionActivity extends Activity {

    static final String KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(KEY_INPUT_PERMISSIONS);
        if (permissions == null)
            finish();
        else
            requestPermissions(permissions, REQUEST_CODE_REQUEST_PERMISSION);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        DMPermission.onRequestPermissionsResult(this, permissions, grantResults);
        Log.i(DMPermission.TAG, "PermissionActivity onRequestPermissionsResult: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DMPermission.onActivityResult(this);
        Log.i(DMPermission.TAG, "PermissionActivity onActivityResult: ");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DMPermission.TAG, "PermissionActivity onDestroy: ");
    }
}
