package com.cc.carmanager.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.CarsBrandListAdapter;
import com.cc.carmanager.bean.BrandListBean;
import com.cc.carmanager.bean.BrandsItemBean;
import com.cc.carmanager.bean.CarsItemBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2017/11/19.
 */

public class CarsDetailListActivity extends BarBaseActivity {
    private List<BrandListBean.BrandCarBean> datas = new ArrayList<>();
    private CarsBrandListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private int brandId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsBrandListAdapter(this, datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        Bundle bundle = getIntent().getExtras();
        brandId = bundle.getInt("id");//读出数据
        setHeader("车辆列表");
        getData();
    }

    private void getData() {
        datas.clear();
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_CAR_LIST, brandId);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                BrandListBean mRecommendBean=gson.fromJson(resultStr,BrandListBean.class);
                if(mRecommendBean.isSuccess() && mRecommendBean.getData()!=null){
                    datas.addAll(mRecommendBean.getData());
                    mAdapter.notifyDataSetChanged();
                }else{
                    ToastUtils.makeShortText("车辆列表加载失败", CarsDetailListActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
