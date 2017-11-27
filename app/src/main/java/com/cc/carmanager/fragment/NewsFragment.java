package com.cc.carmanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsNewsAdapter;
import com.cc.carmanager.bean.CarsNewsBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

/**
 * Created by chenc on 2017/10/23.
 */
public class NewsFragment extends LazyFragment {
    private CarsNewsBean mRecommendBean;
    private CarsNewsAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    private boolean isLoading;


    protected Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.news_fragment_layout);
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
        normalRecyclerViewAdapter = new CarsNewsAdapter(getActivity(), mRecyclerView);
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
        initNews();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
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

    //初始化加载更多数据
    private void getMoreData() {
        normalRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }

    private void getRefreshData() {
        normalRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        normalRecyclerViewAdapter.notifyItemRemoved(normalRecyclerViewAdapter.getItemCount());
    }
}
