package com.cc.carmanager.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.adapt.SimulationExaminationAdapter;
import com.cc.carmanager.bean.QuestionListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.view.ViewPagerScroller;
import com.cc.carmanager.view.VoteSubmitViewPager;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class AnswerQuestionActivity extends BarBaseActivity {

    private VoteSubmitViewPager viewPager;
    List<View> viewItems = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        viewPager = (VoteSubmitViewPager) findViewById(R.id.vote_submit_viewpager);
        initViewPagerScroll();
        loadData();

        setHeader("开始答题");

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    /**
     * 加载数据
     */
    private void loadData() {

        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_QUESTION_LIST, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                QuestionListBean mRecommendBean = gson.fromJson(resultStr, QuestionListBean.class);
                if (mRecommendBean.isSuccess()) {
                    List<QuestionListBean.QuestionItemBean> datas = mRecommendBean.getData();
                    for (int i = 0; i < datas.size(); i++) {
                        viewItems.add(getLayoutInflater().inflate(
                                R.layout.vote_submit_viewpager_item, null));
                    }
                    SimulationExaminationAdapter simpagerAdapter = new SimulationExaminationAdapter(
                            AnswerQuestionActivity.this, viewItems,
                            datas);
                    viewPager.setAdapter(simpagerAdapter);
                    viewPager.getParent()
                            .requestDisallowInterceptTouchEvent(false);
                } else {
                    ToastUtils.makeShortText("新闻加载失败", AnswerQuestionActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });

    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    /**
     * @param index 根据索引值切换页面
     */
    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

}
