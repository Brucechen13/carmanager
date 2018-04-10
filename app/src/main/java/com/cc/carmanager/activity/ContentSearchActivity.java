package com.cc.carmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsNewsBaseAdapter;
import com.cc.carmanager.adapt.CarsNewsSmallAdapter;
import com.cc.carmanager.bean.NewsListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.widget.FilterView;
import com.cc.carmanager.widget.SearchTipsGroupView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2018/3/3.
 */

public class ContentSearchActivity extends AppCompatActivity implements FilterView.OnFilterItemClickListener, View.OnClickListener {

    private SearchTipsGroupView view;
    private Spinner spinner;
    private CarsNewsBaseAdapter normalRecyclerViewAdapter;

    private RecyclerView mRecyclerView;

    private int navId = 2, subId=1, currentPage=0;
    private static int pageSize = 10;

    private TextView searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subId = i+1;
                currentPage = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initView();

        findViewById(R.id.action_bar_back_iv).setOnClickListener(this);
        findViewById(R.id.action_bar_comint_tv).setOnClickListener(this);
        searchText = (TextView) findViewById(R.id.search_input);
    }

    private void initView() {
        List<String> list = new ArrayList<>();
        list.add("新闻");
        list.add("政策");
        list.add("补贴标准");
        list.add("技术标准");
        list.add("技术动态");

        SpinnerAdapter madapter = new SpinnerAdapter(this, list);
        spinner.setAdapter(madapter);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        normalRecyclerViewAdapter = new CarsNewsSmallAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(normalRecyclerViewAdapter);
    }

    private void initNews() {
        String url_news = String.format(Locale.CHINA,NetUrlsSet.URL_NEWS_SEARCHLIST, searchText.getText(), navId, subId, currentPage, pageSize);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                NewsListBean mRecommendBean = gson.fromJson(resultStr, NewsListBean.class);
                if (mRecommendBean.isSuccess()) {
                    normalRecyclerViewAdapter.setDatas(mRecommendBean.getData());
                    currentPage++;
                } else {
                    ToastUtils.makeShortText("新闻加载失败", ContentSearchActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    @Override
    public void onFilterItemClick(String content) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_bar_back_iv:
                this.finish();
                break;
            case R.id.action_bar_comint_tv:
                if(!TextUtils.isEmpty(searchText.getText())) {
                    initNews();
                }else{
                    ToastUtils.makeShortText("请输入检索词", ContentSearchActivity.this);
                }
                break;
        }
    }

    public static class SpinnerAdapter extends BaseAdapter {
        private List<String> mList;
        private Context mContext;

        public SpinnerAdapter(Context context, List<String> bean) {
            mList = bean;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_select, null);
            }
            TextView item_spinner_tv = (TextView) view.findViewById(R.id.title);
            item_spinner_tv.setText(mList.get(i));
            return view;
        }
    }
}
