package com.cc.carmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.MyErrorQuestionListAdapter;
import com.cc.carmanager.bean.QuestionListBean;
import com.cc.carmanager.bean.QuestionResultListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenc on 2018/4/8.
 */

public class MyErrorQuestionActivity extends BarBaseActivity{
    private ListView listView;

    List<QuestionListBean.QuestionItemBean> dataItems;

    private MyErrorQuestionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_error_question);

        listView = (ListView)findViewById(R.id.listview);

        Bundle bundle = getIntent().getExtras();
        dataItems = (List<QuestionListBean.QuestionItemBean>)bundle.getSerializable("data");

        initView();

        setHeader("我的错题");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    private void initView() {

        adapter = new MyErrorQuestionListAdapter(this, dataItems, listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(MyErrorQuestionActivity.this,MyErrorQuestionDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", dataItems.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
