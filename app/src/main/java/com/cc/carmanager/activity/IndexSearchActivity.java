package com.cc.carmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.widget.FilterView;
import com.cc.carmanager.widget.SearchTipsGroupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2018/1/1.
 */

public class IndexSearchActivity extends AppCompatActivity implements FilterView.OnFilterItemClickListener, View.OnClickListener {

    private SearchTipsGroupView view;
    private Spinner spinner;
    private String items[] = {"视频", "么么哒", "动画", "音乐", "猜你喜欢", "最近热门", "影院", "游戏", "好得多"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        view = (SearchTipsGroupView) findViewById(R.id.hot_search_tips);
        view.initViews(items, hot_click);
        view = (SearchTipsGroupView) findViewById(R.id.history_search_tips);
        view.initViews(items, history_click);
        this.spinner = (Spinner) findViewById(R.id.spinner);
        this.initData();

        findViewById(R.id.action_bar_back_iv).setOnClickListener(this);
        findViewById(R.id.action_bar_comint_tv).setOnClickListener(this);
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("新闻");
        list.add("政策");
        list.add("补贴标准");
        list.add("技术标准");
        list.add("技术动态");

        SpinnerAdapter madapter = new SpinnerAdapter(this, list);
        spinner.setAdapter(madapter);
    }

    SearchTipsGroupView.OnItemClick hot_click = new SearchTipsGroupView.OnItemClick(){

        @Override
        public void onClick(int position) {

        }
    };
    SearchTipsGroupView.OnItemClick history_click = new SearchTipsGroupView.OnItemClick(){

        @Override
        public void onClick(int position) {

        }
    };

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
                Intent intent = new Intent(this, ContentSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "推荐阅读");
                intent.putExtras(bundle);
                startActivity(intent);
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
