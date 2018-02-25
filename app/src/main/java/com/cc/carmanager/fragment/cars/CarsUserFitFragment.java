package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsArticleAdapter;
import com.cc.carmanager.bean.ArticleItemBean;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/12/31.
 */

public class CarsUserFitFragment extends LazyFragment {
    private ArrayList<ArticleItemBean> datas = new ArrayList<>();
    private CarsArticleAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsArticleAdapter(this.getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        datas.clear();
        for(int i = 0; i < 10;i ++) {
            ArticleItemBean bean = new ArticleItemBean();
            bean.setIsCost(false);
            bean.setFileSize(100);
            bean.setPicUrl("http://assrt.net/images/logo_sub.jpg");
            bean.setTitle("你好啊啊的期望多群无多");
            bean.setUrl("http://assrt.net/images/logo_sub.jpg");
            datas.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }
}
