package com.cc.carmanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.CarsNewsCommentsAdapter;
import com.cc.carmanager.bean.CommentsItemBean;
import com.cc.carmanager.bean.CommentsListBean;
import com.cc.carmanager.bean.NewsCommentsBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2017/12/30.
 */

public class NewsCommentsActivity extends BarBaseActivity {

    private RecyclerView mRecyclerView;
    private Context context;
    private CarsNewsCommentsAdapter mAdapter;
    private List<CommentsItemBean> datas = new ArrayList<>();
    private int currentPage = 1;
    private static int pageSize=20;

    private boolean isMinePage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_comments);
        initWindow();
        int news_id = 0;
        String title = getIntent().getExtras().getString("title");
        if(title == null){
            title = "页面评论";
            isMinePage = false;
            news_id = getIntent().getExtras().getInt("news_id", 90);
        }
        setHeader(title);

        initData(news_id);
    }

    private void initWindow(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsNewsCommentsAdapter(this, datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(int news_id){
        String url = "";
        if(!isMinePage) {
            url = String.format(Locale.CHINA, NetUrlsSet.URL_COMMENTS_LIST, news_id, currentPage, pageSize);
        }else{
            url = String.format(Locale.CHINA, NetUrlsSet.URL_COMMENT_LIST, currentPage, pageSize);
        }
        VolleyInstance.getVolleyInstance().startRequest(url, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                CommentsListBean mRecommendBean=gson.fromJson(resultStr,CommentsListBean.class);
                if(mRecommendBean.isSuccess()){
                    datas.addAll(mRecommendBean.getData());
                    mAdapter.notifyDataSetChanged();
                }else{
                    ToastUtils.makeShortText("暂无评论", NewsCommentsActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("car", "新闻内容网络数据解析失败");
            }
        });
    }
}
