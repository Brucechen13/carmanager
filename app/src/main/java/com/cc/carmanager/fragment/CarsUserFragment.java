package com.cc.carmanager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsListAdapter;
import com.cc.carmanager.bean.CarsItemBean;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsUserFragment extends LazyFragment {
    private ArrayList<CarsItemBean> datas = new ArrayList<>();
    private CarsListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsListAdapter(this.getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        datas.clear();
        for (int i = 0; i < 10; i++) {
            CarsItemBean bean = new CarsItemBean();
            bean.setBrandName("奔驰");
            bean.setCarName("奔驰"+i);
            bean.setImageUrl("http://assrt.net/images/logo_sub.jpg");
            datas.add(bean);
        }
        for (int i = 0; i < 10; i++) {
            CarsItemBean bean = new CarsItemBean();
            bean.setBrandName("宝马");
            bean.setCarName("宝马"+i);
            bean.setImageUrl("http://assrt.net/images/logo_sub.jpg");
            datas.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }

}
