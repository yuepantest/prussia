package com.example.prussia.ui.module.main;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.prussia.R;
import com.example.prussia.common.base.BaseFragment;
import com.example.prussia.common.manage.Constant;
import com.example.prussia.tools.RefreshTools;
import com.example.prussia.tools.ToastUtil;
import com.example.prussia.ui.adapter.Adapter_main;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author：Prussia
 * date：2018/9/5
 * describe：
 */

public class Fragment_main extends BaseFragment {

    @BindView(R.id.mRv)
    RecyclerView mRv;
    @BindView(R.id.xRefreshView)
    XRefreshView xRefreshView;
    private List<String> list;
    private Adapter_main mAdapter;
    private int page = 1;//默认加载第一页

    @Override
    protected int initLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        setView();
        sendHttp(Constant.HttpCode.HTTP_DIALOG_ORIGINAL);
    }

    /**
     * 设置控件
     */
    private void setView() {
        xRefreshView.setPullLoadEnable(true);//true，可以上拉加载，为false，禁止上拉加载
        xRefreshView.setMoveForHorizontal(true);//解决横向滑动冲突
        //Recyclerview刷新和加载
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            //下拉刷新
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        sendHttp(Constant.HttpCode.HTTP_DIALOG_REFRESH);
                    }
                }, 500);
            }

            //上拉加载
            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sendHttp(Constant.HttpCode.HTTP_DIALOG_LOADMORE);
                    }
                }, 1000);
            }
        });
        //设置适配器
        setAdapter();
    }

    private void sendHttp(final int type) {
        RefreshTools.isShowDialog(type, null, this, xRefreshView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page == 1) {
                    //清除数据
                    list.clear();
                    //添加接口返回数据
                    setData();
                    //设置适配器
                    if (mAdapter == null) {
                        setAdapter();
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }

                } else {
                    List<String> datas = setData2();
                    list.addAll(datas);
                    //设置适配器
                    mAdapter.notifyDataSetChanged();
                }
                page++;//页数累加
                RefreshTools.isCloseDialog(type, true, null, Fragment_main.this, xRefreshView);
            }
        }, 3000);

    }

    /**
     * 创造数据源
     */
    private void setData() {
        for (int i = 0; i < 13; i++) {
            list.add(i + "");
        }
    }

    private List<String> setData2() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            datas.add(i + "a");
        }
        return datas;
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mRv.setLayoutManager(new GridLayoutManager(mContext, 1));
        mAdapter = new Adapter_main(mContext, list);
        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mRv.setAdapter(mAdapter);
        mRv.setNestedScrollingEnabled(false);
        mAdapter.setmOnItemClickListerer(new Adapter_main.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.show(list.get(position));
            }
        });
    }
}
