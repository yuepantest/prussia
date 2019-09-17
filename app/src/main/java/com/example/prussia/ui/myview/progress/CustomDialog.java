package com.example.prussia.ui.myview.progress;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.prussia.R;

/**
 * author：Prussia
 * date：2018/9/5
 * describe：加载进度条
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomDialog instance(Context context) {
        View mView;
        if (android.os.Build.VERSION.SDK_INT > 22) {
            mView = View.inflate(context, R.layout.progress_view_6, null);
        } else {
            mView = View.inflate(context, R.layout.progress_view, null);
        }
        CustomDialog dialog = new CustomDialog(context, R.style.loading_dialog);
        dialog.setContentView(mView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );
        dialog.setCancelable(true);
        return dialog;
    }
}