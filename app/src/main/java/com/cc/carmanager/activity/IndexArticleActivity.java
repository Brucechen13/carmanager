package com.cc.carmanager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.CarsManualAdapter;
import com.cc.carmanager.adapt.CarsNewsBaseAdapter;
import com.cc.carmanager.adapt.CarsNewsSmallAdapter;
import com.cc.carmanager.bean.CarsNewsBean;
import com.cc.carmanager.bean.NewsListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * Created by chenc on 2017/12/12.
 */

public class IndexArticleActivity extends BarBaseActivity{
    private CarsNewsBean mRecommendBean;
    private CarsNewsBaseAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private boolean isLoading;
    private int navId=0, subId=0;
    private int currentPage = 1;
    private static int pageSize=10;

    private int isMinePage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.withbar_fragment_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");//读出数据
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        if(title.equals("维修手册")){
            normalRecyclerViewAdapter = new CarsManualAdapter(this, mRecyclerView, 0);
        }else if(title.equals("基础知识")){
            normalRecyclerViewAdapter = new CarsManualAdapter(this, mRecyclerView, 1);
        }else{
            normalRecyclerViewAdapter = new CarsNewsSmallAdapter(this, mRecyclerView);
        }
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);

        switch (title){
            case "推荐阅读":
                navId = 1;subId=0;
                break;
            case "行业动态":
                navId = 2;subId=0;
                break;
            case "购车指南":
                navId = 4;subId=0;
                break;
            case "用车指南":
                navId = 5;subId=0;
                break;
            case "科普知识":
                navId = 6;subId=0;
                break;
            case "技术资料":
                navId = 7;subId=0;
                break;
            case "我的收藏":
                isMinePage = 1;
                break;
            case "我的点赞":
                isMinePage = 2;
                break;
            default:
                navId = 7;subId=0;
                break;
        }
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

        setHeader(title);
    }


    private void initNews() {
        String url_news = "";
        if(isMinePage == -1) {
            url_news = String.format(NetUrlsSet.URL_NEWS_LIST, navId, subId, currentPage, pageSize);
        }else if(isMinePage == 1){
            url_news = String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_COLLECTLIST, currentPage, pageSize);
        }else if(isMinePage == 2){
            url_news = String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_LIKELIST, currentPage, pageSize);
        }
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsListBean mRecommendBean=gson.fromJson(resultStr,NewsListBean.class);
                if(mRecommendBean.isSuccess()){
                    normalRecyclerViewAdapter.setDatas(mRecommendBean.getData());
                    currentPage++;
                }else{
                    ToastUtils.makeShortText("新闻加载失败", IndexArticleActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }
}
