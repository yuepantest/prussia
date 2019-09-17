package com.example.prussia.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.prussia.R;

import java.util.List;

/**
 * author：Prussia
 * date：2018/9/6
 * describe：上拉加载下拉刷新界面适配器
 */

public class Adapter_main extends BaseRecyclerAdapter<Adapter_main.MyViewHolder_main> {
    private LayoutInflater mInflater;
    private List<String> list;
    private Context context;

    public Adapter_main(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    //为RecyclerView的Item添加监听
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListerer;

    public void setmOnItemClickListerer(OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    @Override
    public int getAdapterItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public MyViewHolder_main getViewHolder(View view) {
        return new MyViewHolder_main(view);
    }

    @Override
    public MyViewHolder_main onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_mian, parent, false);
        MyViewHolder_main viewHolder = new MyViewHolder_main(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder_main holder, final int position, boolean isItem) {
        String str = list.get(position);
        holder.tv.setText(str);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(view, position);
            }
        });
    }

    public class MyViewHolder_main extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder_main(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
