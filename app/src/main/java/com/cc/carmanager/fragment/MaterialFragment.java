package com.cc.carmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.IndexArticleActivity;
import com.cc.carmanager.adapt.CarsArticleAdapter;
import com.cc.carmanager.adapt.CarsNewsBaseAdapter;
import com.cc.carmanager.adapt.CarsNewsSmallAdapter;
import com.cc.carmanager.bean.ArticleItemBean;
import com.cc.carmanager.bean.NewsItemBean;
import com.cc.carmanager.bean.NewsListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.view.ImageTextView;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhouwei on 17/4/23.
 */

public class MaterialFragment extends LazyFragment{
    private ArrayList<NewsItemBean> mOneNewsItemList = new ArrayList<>();
    private CarsNewsBaseAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private static int navId = 7, subId = 0, pageSize=10;
    private int currentPage= 0 ;


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.material_fragment_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        normalRecyclerViewAdapter = new CarsNewsSmallAdapter(getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        getIndexNews();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }


    private void getIndexNews() {
        mOneNewsItemList.clear();
        String url_news = String.format(NetUrlsSet.URL_NEWS_LIST, navId, subId, currentPage, pageSize);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsListBean mRecommendBean=gson.fromJson(resultStr,NewsListBean.class);
                if(mRecommendBean.isSuccess()){
                    mOneNewsItemList.addAll(mRecommendBean.getData());
                    normalRecyclerViewAdapter.setDatas(mOneNewsItemList);
                    pageSize++;
                }else{
                    ToastUtils.makeShortText("新闻加载失败", MaterialFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
        normalRecyclerViewAdapter.notifyDataSetChanged();
    }

    //初始化加载更多数据
    private void getMoreData() {
        String url_news = String.format(NetUrlsSet.URL_NEWS_LIST, navId, subId, currentPage, pageSize);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsListBean mRecommendBean=gson.fromJson(resultStr,NewsListBean.class);
                if(mRecommendBean.isSuccess()){
                    mOneNewsItemList.addAll(mRecommendBean.getData());
                    normalRecyclerViewAdapter.setDatas(mOneNewsItemList);
                    pageSize++;
                }else{
                    ToastUtils.makeShortText("新闻加载失败", MaterialFragment.this.getContext());
                }
            }
            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
        normalRecyclerViewAdapter.notifyDataSetChanged();
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }

    private void getRefreshData() {
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_LIST, navId, subId, 0, pageSize);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsListBean mRecommendBean=gson.fromJson(resultStr,NewsListBean.class);
                if(mRecommendBean.isSuccess()){
                    for(NewsItemBean bean : mRecommendBean.getData()){
                        if(!mOneNewsItemList.contains(bean)){
                            mOneNewsItemList.add(bean);
                        }
                    }
                    normalRecyclerViewAdapter.setDatas(mOneNewsItemList);
                }else{
                    ToastUtils.makeShortText("新闻加载失败", MaterialFragment.this.getContext());
                }
            }
            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
        normalRecyclerViewAdapter.notifyDataSetChanged();
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }
}