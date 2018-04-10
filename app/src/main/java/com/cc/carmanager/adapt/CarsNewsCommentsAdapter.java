package com.cc.carmanager.adapt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.CarsDetailActivity;
import com.cc.carmanager.adapt.holder.CommonViewHolder;
import com.cc.carmanager.bean.CommentsItemBean;
import com.cc.carmanager.bean.NewsCommentsBean;

import java.util.List;

/**
 * Created by chenc on 2018/1/2.
 */

public class CarsNewsCommentsAdapter extends RecyclerView.Adapter<CommonViewHolder>{

    private RecyclerView recyclerView;
    private List<CommentsItemBean> datas;

    public CarsNewsCommentsAdapter(Context context, List<CommentsItemBean> listItem, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.datas = listItem;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder newsVH = CommonViewHolder.getViewHolder(parent, R.layout.item_news_comment);
        return newsVH;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setText(R.id.tv_username,datas.get(position).getUserName());
        holder.setText(R.id.tv_comment,datas.get(position).getContent());
        holder.setText(R.id.tv_time,datas.get(position).getAddTime().getFullTime());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
