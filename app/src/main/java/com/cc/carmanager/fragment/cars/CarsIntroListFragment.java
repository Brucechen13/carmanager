package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsIntroListAdapter;
import com.cc.carmanager.bean.CarSeriesBean;
import com.cc.carmanager.bean.CarsItemBean;
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

public class CarsIntroListFragment extends LazyFragment{
    private ArrayList<CarSeriesBean.CarSerieBean> datas = new ArrayList<>();
    private CarsIntroListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private int carId;
    private int yearType;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsIntroListAdapter(this.getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        carId = bundle.getInt("carId");
        yearType = bundle.getInt("yearType");

        getData();
    }

    private void getData() {
        datas.clear();
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_CAR_SERIES, carId, yearType);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarSeriesBean mRecommendBean = gson.fromJson(resultStr, CarSeriesBean.class);
                if (mRecommendBean.isSuccess()) {
                    datas.addAll(mRecommendBean.getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.makeShortText("未查询到车型", CarsIntroListFragment.this.getContext());
                }
            }
            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

}
