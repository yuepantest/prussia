package com.example.prussia.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prussia.common.manage.MyApplication;
import com.example.prussia.http.Api;
import com.example.prussia.http.ApiService;
import com.example.prussia.tools.Check;
import com.example.prussia.tools.EventBus.Event;
import com.example.prussia.tools.EventBus.EventBusUtil;
import com.example.prussia.tools.ToastUtil;
import com.example.prussia.ui.myview.progress.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：岳攀 on 2016/8/27
 * QQ号：905354677
 * 作用：常用框架Fragment
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 上下文
     */
    protected Context mContext;
    public ApiService apiService;
    public MyApplication application;
    private CustomDialog progress;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        application = (MyApplication) mContext.getApplicationContext();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (initLayout() == -1) {
            //需要懒加载
            view = initLayoutView();
        } else {
            //不需要懒加载
            view = View.inflate(getActivity(), initLayout(), null);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected View initLayoutView() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //apiService
        apiService = Api.getApiService();//网络请求
        initData();
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    //设置activity的布局文件
    protected abstract int initLayout();

    /**
     * 当孩子需要初始化数据，或者联网请求绑定数据，展示数据的 等等可以重写该方法
     */
    protected abstract void initData();

    public CustomDialog getDialog() {
        if (progress == null) {
            progress = CustomDialog.instance(getActivity());
            progress.setCancelable(true);
        }
        return progress;
    }

    public void hideProgress() {
        if (progress != null)
            progress.hide();
    }

    public boolean showProgress() {
        if (Check.isNetworkConnected(mContext)) {
            try {
                getDialog().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            ToastUtil.show("请检查网络");
            return false;
        }
    }

    public void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgress();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
