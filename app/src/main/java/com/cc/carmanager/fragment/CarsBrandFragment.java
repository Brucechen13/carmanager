package com.cc.carmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsBrandAdapter;
import com.cc.carmanager.bean.BrandsItemBean;
import com.cc.carmanager.bean.CarsBrandBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.HanziToPinyin;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.view.TypeFilterActivity;
import com.cc.carmanager.view.PriceFilterActivity;
import com.cc.carmanager.widget.SideBar;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by zhouwei on 17/4/23.
 */

public class CarsBrandFragment extends LazyFragment implements SideBar
        .OnTouchingLetterChangedListener, TextWatcher, View.OnClickListener {

    private TextView mFooterView;

    private CarsBrandAdapter mAdapter;
    private CarsBrandBean datas;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carbuy_fragment_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.cars_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsBrandAdapter(getActivity(),  mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        SideBar mSideBar = (SideBar) findViewById(R.id.school_friend_sidrbar);
        TextView mDialog = (TextView) findViewById(R.id.school_friend_dialog);

        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);

       findViewById(R.id.filter_price).setOnClickListener(this);
        findViewById(R.id.filter_brand).setOnClickListener(this);
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        initDatas();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    private void initDatas() {
        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_CAR, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                datas=gson.fromJson(resultStr,CarsBrandBean.class);
                mAdapter.setDatas(datas);
            }

            @Override
            public void failure() {
                Log.d("aaa", "车型网络数据解析失败");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onTouchingLetterChanged(String s) {

        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mRecyclerView.smoothScrollToPosition(position);
        } else if (s.contains("#")) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.filter_price:
                i = new Intent(this.getActivity(), PriceFilterActivity.class);
                this.getActivity().startActivity(i);
                break;
            case R.id.filter_brand:
                i = new Intent(this.getActivity(), TypeFilterActivity.class);
                this.getActivity().startActivity(i);
                break;
            default:
                break;
        }
    }
}
