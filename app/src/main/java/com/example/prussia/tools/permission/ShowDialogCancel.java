package com.example.prussia.tools.permission;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.prussia.R;

/**
 * 自定义弹出dialog
 * Created by DMing on 2017/7/4.
 */

class ShowDialogCancel {

    private Dialog mDialog;
    private TextView contentTv;
    private OnClick onClick;

    ShowDialogCancel(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDialog = new Dialog(context, R.style.PermissionDialogStyle);
        View mDialogView = View.inflate(context, R.layout.dialog_permission_cancel, null);
        mDialog.setContentView(mDialogView);
        Window dialogWindow = mDialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            lp.width = (int) (dm.widthPixels * 0.75f);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(lp);
        }
        contentTv = mDialogView.findViewById(R.id.contentTv);
        mDialogView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) onClick.confirm();
                dismiss();
            }
        });
        mDialogView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) onClick.cancel();
                dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
    }

    void show(String msg) {
        contentTv.setText(msg);
        if (!mDialog.isShowing()) {
            try {
                mDialog.show();//显示对话框
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
                mDialog.dismiss();
            }
        }
    }

    private void dismiss() {
        mDialog.dismiss();
    }

    interface OnClick {
        void confirm();

        void cancel();
    }

    ShowDialogCancel setOnClick(OnClick onClick) {
        this.onClick = onClick;
        return this;
    }

}
