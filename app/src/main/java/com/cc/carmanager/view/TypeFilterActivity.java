package com.cc.carmanager.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsFilterAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2017/11/18.
 */

public class TypeFilterActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private CarsFilterAdapter recyclerViewAdapter;
    private Map<String, List<String>> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_type);
        initData();

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        recyclerViewAdapter = new CarsFilterAdapter(this, itemList, CarsFilterAdapter.CarsFilterType.BranFilter);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initData(){
        itemList = new HashMap<>();
        List<String> items = new ArrayList<>();
        items.add("轿车");
        items.add("全部轿车");
        items.add("微型车");
        items.add("小型车");
        itemList.put(items.get(0),items);
        items = new ArrayList<>();
        items.add("SUV");
        itemList.put(items.get(0),items);
        items = new ArrayList<>();
        items.add("MPV");
        items.add("跑车");
        itemList.put(items.get(0),items);
        items = new ArrayList<>();
        items.add("跑车");
        itemList.put(items.get(0),items);
    }
}
