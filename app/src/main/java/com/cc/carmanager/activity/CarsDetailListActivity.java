package com.cc.carmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsListAdapter;
import com.cc.carmanager.bean.CarsItemBean;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/11/19.
 */

public class CarsDetailListActivity extends AppCompatActivity {
    private ArrayList<CarsItemBean> datas = new ArrayList<>();
    private CarsListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("info", "filter show");
        setContentView(R.layout.activity_normal_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsListAdapter(this, datas, mRecyclerView);
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
