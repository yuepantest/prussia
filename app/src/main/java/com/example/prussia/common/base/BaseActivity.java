package com.example.prussia.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.prussia.common.manage.AppManager;
import com.example.prussia.common.manage.MyApplication;
import com.example.prussia.http.Api;
import com.example.prussia.http.ApiService;
import com.example.prussia.tools.Check;
import com.example.prussia.tools.EventBus.Event;
import com.example.prussia.tools.EventBus.EventBusUtil;
import com.example.prussia.tools.ToastUtil;
import com.example.prussia.ui.myview.progress.CustomDialog;
import com.google.gson.Gson;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {
    protected Context mContext;
    public ApiService apiService;
    public MyApplication application;
    public Gson mGson;
    private CustomDialog progress;

    /**
     * 对context进行初始化，并将当前的Activity加入到堆栈中，便于管理
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置不能横屏，防止生命周期的改变
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        //设置沉侵式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xff000000);
        }
        //设置布局
        if (initLayout() != 0) setContentView(initLayout());
        //ButterKnife
        ButterKnife.bind(this);
        //注册EventBus
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        //apiService
        apiService = Api.getApiService();//网络请求
        application = (MyApplication) getApplication();
        //上下文
        mContext = this;
        mGson = new Gson();
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

    //发送事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    //发送粘性事件
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        //关闭对话框
        dismissDialog();
        //注销EventBus
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * 当孩子需要初始化数据，或者联网请求绑定数据，展示数据的 等等可以重写该方法
     */
    protected abstract void initData();

    // Progress
    public CustomDialog getProgress() {
        if (progress == null) {
            progress = CustomDialog.instance(this);
            progress.setCancelable(true);
        }
        return progress;
    }

    public void hideProgress() {
        if (progress != null)
            progress.hide();
    }

    public boolean showProgress() {
        if (Check.isNetworkConnected(this)) {
            if (!this.isFinishing()) {
                try {
                    getProgress().show();
                } catch (Exception e) {
                }
                return true;
            } else {
                return false;
            }
        } else {
            ToastUtil.show("网络连接异常");
            return false;
        }
    }

    public void dismissDialog() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
