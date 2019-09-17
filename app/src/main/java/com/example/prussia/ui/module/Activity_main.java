package com.example.prussia.ui.module;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.prussia.R;
import com.example.prussia.common.base.BaseActivity;
import com.example.prussia.common.base.BaseFragment;
import com.example.prussia.common.manage.AppManager;
import com.example.prussia.ui.module.find.Fragment_find;
import com.example.prussia.ui.module.main.Fragment_main;
import com.example.prussia.ui.module.my.Fragment_my;
import com.example.prussia.ui.module.news.Fragment_news;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class Activity_main extends BaseActivity {
    /**
     * 控件初始化
     */
    @BindView(R.id.rg_main)
    RadioGroup mRg_main;
    /**
     * 用来储存fragment
     */
    private List<BaseFragment> mBaseFragment;
    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;
    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initFragment();
        setListener();
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new Fragment_main());//首页Fragment
        mBaseFragment.add(new Fragment_find());//发现Fragment
        mBaseFragment.add(new Fragment_news());//消息Fragment
        mBaseFragment.add(new Fragment_my());//我的Fragment
    }

    private void setListener() {
        //设置RadioGroup的监听
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        mRg_main.check(R.id.rb_man);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_man://主界面
                    position = 0;
                    break;
                case R.id.rb_rankinglist://发现界面
                    position = 1;
                    break;
                case R.id.rb_news://消息界面
                    position = 2;
                    break;
                case R.id.rb_my://我的界面
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent, to);
        }
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl_content, to).commitAllowingStateLoss();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commitAllowingStateLoss();
                }
            }
        }
    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    /**
     * 点击两次回退建退出
     */
    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出进程
        AppManager.getAppManager().AppExit(this);
    }

    /**
     * 不保存视图去解决fragment重复问题（简单暴力后期需要完善）
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }
}
