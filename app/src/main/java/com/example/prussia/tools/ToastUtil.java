package com.example.prussia.tools;

import android.view.Gravity;
import android.widget.Toast;

import com.example.prussia.common.manage.MyApplication;

/**
 * Created by QD on 2017/7/19.
 */

public class ToastUtil {
    public static Toast mToast;

    public static void show(String text) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
            } else {
                //如果当前Toast没有消失， 直接显示内容，不需要重新设置
                mToast.setText(text);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
