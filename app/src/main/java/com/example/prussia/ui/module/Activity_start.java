package com.example.prussia.ui.module;

import android.content.Intent;
import android.os.Handler;

import com.example.prussia.R;
import com.example.prussia.common.base.BaseActivity;


public class Activity_start extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Activity_start.this, Activity_main.class));
                finish();
            }
        }, 2000);
    }
}
