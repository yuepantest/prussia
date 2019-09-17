package com.example.prussia.ui.myview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.prussia.R;
import com.example.prussia.interfaces.MyDialogListener;

/**
 * author：Prussia
 * date：2018/12/21
 * describe：
 */

public class MyDialog {

    private final TextView tvBt0;
    private final TextView tvBt1;
    private Dialog dialog;

    /**
     * 基本对话框
     *
     * @param context
     * @param title
     * @param cont
     * @param tv0
     * @param tv1
     * @param listener
     */
    public MyDialog(Context context, String title, String cont, String tv0, String tv1, MyDialogListener listener) {
        this(context, title, cont, tv0, tv1, listener, R.color.colorHei, R.color.colorHei, true);
    }

    /**
     * 是否可以取消
     *
     * @param context
     * @param title
     * @param cont
     * @param tv0
     * @param tv1
     * @param listener
     * @param cancelable
     */
    public MyDialog(Context context, String title, String cont, String tv0, String tv1, MyDialogListener listener, boolean cancelable) {
        this(context, title, cont, tv0, tv1, listener, R.color.colorHei, R.color.colorHei, cancelable);
    }

    /**
     * 自定义字体颜色
     *
     * @param context
     * @param title
     * @param cont
     * @param tv0
     * @param tv1
     * @param listener
     * @param coler0
     * @param coler1
     * @param cancelable
     */
    public MyDialog(Context context, String title, String cont, String tv0, String tv1, MyDialogListener listener, int coler0, int coler1, boolean cancelable) {
        this(context, title, cont, tv0, tv1, listener, R.color.colorHei, R.color.colorHei, coler0, coler1, false, cancelable);
    }

    /**
     * 自定义全部参数
     *
     * @param context
     * @param title
     * @param cont
     * @param tv0
     * @param tv1
     * @param listener
     * @param titleColer
     * @param contColer
     * @param bt0Coler
     * @param bt1Coler
     * @param hideBt1
     * @param cancelable
     */
    public MyDialog(Context context, String title, String cont, String tv0, String tv1, final MyDialogListener listener, int titleColer, int contColer, int bt0Coler, int bt1Coler, boolean hideBt1, boolean cancelable) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog = DialogTools.show(context, dialogView);
        //设置是否能取消
        dialog.setCancelable(cancelable);
        //标题
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        //内容
        TextView tvCont = dialogView.findViewById(R.id.tvCont);
        //竖向分隔线
        View btDevice = dialogView.findViewById(R.id.btDevice);
        //按钮1
        tvBt0 = dialogView.findViewById(R.id.tvBt0);
        //按钮2
        tvBt1 = dialogView.findViewById(R.id.tvBt1);
        //设置标题
        if (title != null && !title.equals("")) {
            tvTitle.setText(title);
            tvTitle.setTextColor(context.getResources().getColor(titleColer));
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        //设置内容
        if (cont != null && !cont.equals("")) {
            tvCont.setText(cont);
            tvCont.setTextColor(context.getResources().getColor(contColer));
        }
        //设置按钮1
        if (tv0 != null && !tv0.equals("")) {
            tvBt0.setText(tv0);
        } else {
            tvBt0.setText("确定");
        }
        tvBt0.setTextColor(context.getResources().getColor(bt0Coler));
        tvBt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(0);
                }
                dialog.dismiss();
            }
        });
        if (hideBt1) {
            btDevice.setVisibility(View.GONE);
            tvBt1.setVisibility(View.GONE);
        } else {
            //设置按钮2
            btDevice.setVisibility(View.VISIBLE);
            tvBt1.setVisibility(View.VISIBLE);
            if (tv1 != null && !tv1.equals("")) {
                tvBt1.setText(tv1);
                tvBt1.setTextColor(context.getResources().getColor(bt1Coler));
            }
            tvBt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(1);
                    }
                    dialog.dismiss();
                }
            });
        }
    }

    //关闭弹窗
    public void dismiss() {
        if (dialog != null) {
            tvBt0.setOnClickListener(null);
            tvBt1.setOnClickListener(null);
            dialog.dismiss();
        }
    }
}
