package com.cc.carmanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsNewsSmallAdapter;
import com.cc.carmanager.adapt.CarsUserListAdapter;
import com.cc.carmanager.bean.CarsItemBean;
import com.cc.carmanager.bean.NewsItemBean;
import com.cc.carmanager.bean.NewsListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsUserFragment extends LazyFragment {
    private ArrayList<NewsItemBean> datas = new ArrayList<>();
    private CarsNewsSmallAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private String searchText;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsNewsSmallAdapter(this.getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        getData();
    }

    public void setSearchText(String searchText){
        this.searchText = searchText;
        getData();
    }

    private void getData() {
        datas.clear();
        String url_news = "";
        if(searchText != null){
            url_news = String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_SEARCHLIST, searchText, 5, 0, 0, 10);
        }else{
            url_news = String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_LIST, 5, 0, 0, 10);
        }
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsListBean mRecommendBean=gson.fromJson(resultStr,NewsListBean.class);
                if(mRecommendBean.isSuccess()){
                    mAdapter.setDatas(mRecommendBean.getData());
                }else{
                    ToastUtils.makeShortText("新闻加载失败", CarsUserFragment.this.getContext());
                }
            }
            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
        mAdapter.notifyDataSetChanged();
    }

}
