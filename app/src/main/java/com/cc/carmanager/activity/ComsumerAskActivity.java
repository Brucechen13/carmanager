package com.cc.carmanager.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.ConsumerAskAdapter;
import com.cc.carmanager.bean.ConsumerAskListBean;
import com.cc.carmanager.bean.PostStatusBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.view.VoteSubmitViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2018/4/8.
 */

public class ComsumerAskActivity extends BarBaseActivity {

    private static int pageSize = 10;
    private int currentPage = 1;
    private ConsumerAskAdapter mAdapter;
    private List<ConsumerAskListBean.ConsumerAskItemBean> datas = new ArrayList<>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosumer_ask);
        editText = (EditText) findViewById(R.id.chat_content);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.chat_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new ConsumerAskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initData();

        findViewById(R.id.chat_send).setOnClickListener(this);

        setHeader("咨询回复");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.chat_send:
                String input = editText.getText().toString();
                if(input.equals("")){
                    ToastUtils.makeShortText("请输入咨询问题", ComsumerAskActivity.this);
                }else{
                    //增加咨询
                    Map<String, String> params = new HashMap<>();
                    params.put("content", input);
                    VolleyInstance.getVolleyInstance().startJsonObjectPost(NetUrlsSet.URL_CONSUMER_ADD, params, new VolleyResult() {
                        @Override
                        public void success(String resultStr) {
                            Log.e("car", resultStr);
                            Gson gson=new Gson();
                            PostStatusBean mRecommendBean=gson.fromJson(resultStr,PostStatusBean.class);
                            if(mRecommendBean.isSuccess()){
                                ToastUtils.makeLongText("咨询上传，请等待答复", ComsumerAskActivity.this);
                                editText.setText("");
                                editText.setFocusable(false);
                            }else{
                                ToastUtils.makeShortText("上传失败", ComsumerAskActivity.this);
                            }
                        }
                        @Override
                        public void failure() {
                            Log.d("car", "新闻内容网络数据解析失败");
                        }
                    });
                }
                break;
        }
    }

    private void initData(){
        String url_news = String.format(NetUrlsSet.URL_CONSUMER_LIST, currentPage, pageSize);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                ConsumerAskListBean mRecommendBean=gson.fromJson(resultStr,ConsumerAskListBean.class);
                if(mRecommendBean.isSuccess()){
                    datas.addAll(mRecommendBean.getData());
                    mAdapter.setDatas(datas);
                }else{
                    ToastUtils.makeShortText("新闻加载失败", ComsumerAskActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }
}
