package com.cc.carmanager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.CarsNewsSmallAdapter;
import com.cc.carmanager.bean.CarsNewsBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.google.gson.Gson;

/**
 * Created by chenc on 2017/12/12.
 */

public class IndexArticleActivity extends BarBaseActivity{
    private CarsNewsBean mRecommendBean;
    private CarsNewsSmallAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private boolean isLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.withbar_fragment_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        normalRecyclerViewAdapter = new CarsNewsSmallAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.e("info", "onScrolled" + dy);
//            }
//        });

        initNews();

        Bundle bundle = getIntent().getExtras();
        setHeader(bundle.getString("title"));
    }


    private void initNews() {
        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_NEW, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                mRecommendBean=gson.fromJson(resultStr,CarsNewsBean.class);
                normalRecyclerViewAdapter.setDatas(mRecommendBean);
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }
}
