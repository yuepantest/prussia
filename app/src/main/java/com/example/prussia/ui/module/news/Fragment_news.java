package com.example.prussia.ui.module.news;

import android.view.View;
import android.widget.Button;
import com.example.prussia.R;
import com.example.prussia.common.base.BaseFragment;
import com.example.prussia.tools.Check;
import butterknife.BindView;
/**
 * author：Prussia
 * date：2018/9/5
 * describe：
 */

public class Fragment_news extends BaseFragment {
    @BindView(R.id.bt)
    Button bt;//滑动控件

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setP();
            }
        });
    }

    private void setP() {
        if (Check.isCameraPermission(mContext)) {
            //有录音权限
        } else {
            //没有录音权限
        }
    }
}
