package com.cc.carmanager.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsArticleAdapter;
import com.cc.carmanager.bean.ArticleItemBean;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by zhouwei on 17/4/23.
 */

public class MaterialFragment extends LazyFragment{
    private ArrayList<ArticleItemBean> mOneNewsItemList = new ArrayList<>();
    private CarsArticleAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    private boolean isLoading;
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.material_fragment_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRefreshData();
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        normalRecyclerViewAdapter = new CarsArticleAdapter(getActivity(), mOneNewsItemList, mRecyclerView);
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == normalRecyclerViewAdapter.getItemCount()) {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getMoreData();
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
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
        for(int i = 0; i < 10;i ++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setFileSize(100);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
//        MySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(
//                RequestSingletonFactory.getInstance().getGETStringRequest(getActivity(), URLs.getUrl(tabName), new Response.Listener() {
//                    @Override
//                    public void onResponse(Object response) {
//                        JSONObject obj;
//                        try {
//                            mOneNewsItemList.clear();
//                            obj = new JSONObject(response.toString());
//                            JSONArray itemArray = obj.getJSONArray(URLs.getUrlTag(tabName));
//                            ArrayList<OneNewsItemBean> newsList = new Gson().fromJson(itemArray.toString(), Global.NewsItemType);
//                            mOneNewsItemList.addAll(newsList);
//                            normalRecyclerViewAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }));
    }

    //初始化加载更多数据
    private void getMoreData() {
        for (int i = 0; i < 10; i++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }

    private void getRefreshData() {
        for (int i = 0; i < 2; i++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            mOneNewsItemList.add(0, bean);
        }
        normalRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }
}