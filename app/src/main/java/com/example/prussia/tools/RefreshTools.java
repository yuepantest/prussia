package com.example.prussia.tools;

import com.andview.refreshview.XRefreshView;
import com.example.prussia.common.base.BaseActivity;
import com.example.prussia.common.base.BaseFragment;
import com.example.prussia.common.manage.Constant;

/**
 * author：Prussia
 * date：2018/5/3
 * describe：
 */

public class RefreshTools {

    public static void isShowDialog(int type, BaseActivity activity, BaseFragment fragment, XRefreshView xRefreshView) {
        if (type == Constant.HttpCode.HTTP_DIALOG_ORIGINAL) {
            //原始的刷新
            if (activity != null) {
                activity.showProgress();
            } else if (fragment != null) {
                fragment.showProgress();
            }
        } else {
            //不需要展示刷新
        }

    }

    public static void isCloseDialog(int type, boolean isSucceed, BaseActivity activity, BaseFragment fragment, XRefreshView xRefreshView) {
        if (type == Constant.HttpCode.HTTP_DIALOG_ORIGINAL) {
            //原始的刷新
            if (activity != null) {
                activity.hideProgress();
            } else if (fragment != null) {
                fragment.hideProgress();
            }
        } else if (type == Constant.HttpCode.HTTP_DIALOG_REFRESH) {
            //刷新
            if (isSucceed) {
                xRefreshView.stopRefresh(true);
            } else {
                xRefreshView.stopRefresh(false);
            }
        } else if (type == Constant.HttpCode.HTTP_DIALOG_LOADMORE) {
            //加载
            if (isSucceed) {
                xRefreshView.stopLoadMore(true);
            } else {
                xRefreshView.stopLoadMore(false);
            }
        }
    }
}
