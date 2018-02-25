package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsSellerAdapter;
import com.cc.carmanager.bean.CarsSellerBean;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsSellerFragment extends LazyFragment {
    private ArrayList<CarsSellerBean> datas = new ArrayList<>();
    private CarsSellerAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsSellerAdapter(this.getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        datas.clear();
        for (int i = 0; i < 10; i++) {
            CarsSellerBean bean = new CarsSellerBean();
            bean.setName("奔驰");
            bean.setPlace("奔驰"+i);
            datas.add(bean);
        }
        mAdapter.notifyDataSetChanged();
    }

}
