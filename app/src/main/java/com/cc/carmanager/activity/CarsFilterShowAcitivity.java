package com.cc.carmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsFilterAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/11/19.
 */

public class CarsFilterShowAcitivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CarsFilterAdapter recyclerViewAdapter;
    private List<String> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_price);
        initData();

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        recyclerViewAdapter = new CarsFilterAdapter(this, itemList);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData(){
        itemList = new ArrayList<>();
        itemList.add("五万以下");
        itemList.add("五万到10万");
        itemList.add("10万到20万");
        itemList.add("20万到30万");
    }
}
