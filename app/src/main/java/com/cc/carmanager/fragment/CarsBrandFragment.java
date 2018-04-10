package com.cc.carmanager.fragment;

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
import com.cc.carmanager.bean.CarsBrandBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.widget.SideBar;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

/**
 * Created by zhouwei on 17/4/23.
 */

public class CarsBrandFragment extends LazyFragment implements View.OnClickListener {
//implements SideBar
//        .OnTouchingLetterChangedListener, TextWatcher,

    private TextView mFooterView;

    private CarsBrandAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private String searchText = null;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carbuy_fragment_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.cars_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsBrandAdapter(getActivity(),  mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);


//        SideBar mSideBar = (SideBar) findViewById(R.id.school_friend_sidrbar);
//        TextView mDialog = (TextView) findViewById(R.id.school_friend_dialog);
//
//        mSideBar.setTextView(mDialog);
//        mSideBar.setOnTouchingLetterChangedListener(this);
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

    public void setSearchText(String searchText){
        this.searchText = searchText;
        initDatas();
    }

    private void initDatas() {
        String url = NetUrlsSet.URL_CAR_BRAND;
        if(searchText != null){
            url = String.format(NetUrlsSet.URL_CAR_BRANDSEARCH, searchText);
        }
        VolleyInstance.getVolleyInstance().startRequest(url, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                CarsBrandBean mRecommendBean=gson.fromJson(resultStr,CarsBrandBean.class);
                if(mRecommendBean.isSuccess()){
                    mAdapter.setDatas(mRecommendBean.getData());
                    mAdapter.notifyDataSetChanged();
                }else{
                    ToastUtils.makeShortText("新闻加载失败", CarsBrandFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

//
//    @Override
//    public void onTouchingLetterChanged(String s) {
//
//        int position = 0;
//        // 该字母首次出现的位置
//        if (mAdapter != null) {
//            position = mAdapter.getPositionForSection(s.charAt(0));
//        }
//        if (position != -1) {
//            mRecyclerView.smoothScrollToPosition(position);
//        } else if (s.contains("#")) {
//            mRecyclerView.smoothScrollToPosition(0);
//        }
//    }

    @Override
    public void onClick(View view) {
    }
}
