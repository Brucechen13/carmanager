package com.cc.carmanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.CarsNewsCommentsAdapter;
import com.cc.carmanager.bean.NewsCommentsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/12/30.
 */

public class NewsCommentsActivity extends BarBaseActivity {

    private RecyclerView mRecyclerView;
    private Context context;
    private CarsNewsCommentsAdapter mAdapter;
    private List<NewsCommentsBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_comments);
        initWindow();
        setHeader("页面评论");
        initData();
    }

    private void initWindow(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsNewsCommentsAdapter(this, datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        datas.clear();
        for(int i = 0;i < 10; i ++){
            NewsCommentsBean bean = new NewsCommentsBean();
            datas.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
