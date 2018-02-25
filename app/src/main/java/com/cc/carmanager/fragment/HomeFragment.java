package com.cc.carmanager.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.IndexArticleActivity;
import com.cc.carmanager.activity.IndexSearchActivity;
import com.cc.carmanager.adapt.CarsNewsRecommandAdapter;
import com.cc.carmanager.bean.CarsNewsBean;
import com.cc.carmanager.behavior.uc.UcNewsHeaderPagerBehavior;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.GlideImageLoader;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ScreenUtil;
import com.cc.carmanager.view.ImageTextView;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/4/23.
 */

public class HomeFragment extends LazyFragment implements UcNewsHeaderPagerBehavior.OnPagerStateListener, View.OnClickListener {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private int index;
    private final int textPadding = 5;//dp
    private final int barWidth = 42;//

    private UcNewsHeaderPagerBehavior mPagerBehavior;
    private CarsNewsRecommandAdapter recommandRecyclerViewAdapter;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.home_fragment_layout);
        Resources res = getResources();

        Bundle bundle = getArguments();
//		tabName = bundle.getString(INTENT_STRING_TABNAME);
        //index = bundle.getInt(INTENT_INT_INDEX);

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.fragment_tabmain_indicator);
        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(getActivity(), barWidth));
        indicator.setScrollBar(colorBar);

        viewPager.setOffscreenPageLimit(3);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        mPagerBehavior = (UcNewsHeaderPagerBehavior) ((CoordinatorLayout.LayoutParams) findViewById(R.id.id_uc_news_header_pager).getLayoutParams()).getBehavior();
        mPagerBehavior.setPagerStateListener(this);

        findViewById(R.id.recommand_more).setOnClickListener(this);

        findViewById(R.id.search_input).setOnClickListener(this);

        initBanner();
        initButtons();
        initRecommands();
    }

    private void initButtons(){
        ImageTextView mKnowledge = (ImageTextView) findViewById(R.id.menu_knowledge_id);
        ImageTextView mManual = (ImageTextView) findViewById(R.id.menu_manual_id);
        ImageTextView mArticle = (ImageTextView) findViewById(R.id.menu_article_id);
        ImageTextView mStandard = (ImageTextView) findViewById(R.id.menu_standard_id);
        ImageTextView mMaterial = (ImageTextView) findViewById(R.id.menu_material_id);
        initButton(mKnowledge, "知识大全",R.mipmap.icon_know, IndexArticleActivity.class);
        initButton(mManual, "手册查询",R.mipmap.icon_book, IndexArticleActivity.class);
        initButton(mArticle, "技术文章",R.mipmap.icon_techarticle, IndexArticleActivity.class);
        initButton(mStandard, "技术标准",R.mipmap.icon_techstand, IndexArticleActivity.class);
        initButton(mMaterial, "自学教材",R.mipmap.icon_selflearn, IndexArticleActivity.class);
    }
    private void initButton(ImageTextView view, final String title, int imageUrl, final Class jump_class) {
        view.setText(title);
        view.setImg(imageUrl);
        view.setClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), jump_class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initBanner(){
        List<String> images = new ArrayList<>();
        for(int i = 0; i < 6; i ++){
            images.add("http://img.pcauto.com.cn/images/pcautogallery/modle/article/201710/29/15092917392405740_660.webp");
        }
        Banner banner=(Banner) findViewById(R.id.item_recyclerview_header_banner);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initRecommands(){

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.news_recommend);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        recommandRecyclerViewAdapter = new CarsNewsRecommandAdapter(getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(recommandRecyclerViewAdapter);
    }

    private void initNews() {
        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_NEW, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                CarsNewsBean mRecommendBean=gson.fromJson(resultStr,CarsNewsBean.class);
                mRecommendBean.setNewslist(mRecommendBean.getNewslist().subList(0, 2));
                recommandRecyclerViewAdapter.setDatas(mRecommendBean);
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        initNews();
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        Log.d("cccc", "Fragment 显示 " + this);
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
        Log.d("cccc", "Fragment 掩藏 " + this);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        Log.d("cccc", "Fragment所在的Activity onPause, onPauseLazy " + this);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        Log.d("cccc", "Fragment View将被销毁 " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

    @Override
    public void onPagerClosed() {

    }

    @Override
    public void onPagerOpened() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.recommand_more:
                intent = new Intent(getActivity(), IndexArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "推荐阅读");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.search_input:
                intent = new Intent(getActivity(), IndexSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private String[] tabNames = {"新闻", "政策", "补贴", "标准", "动态"};

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            convertView = inflate.inflate(R.layout.tab_top, container, false);
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setPadding(ScreenUtil.dp2px(getActivity(), textPadding), 0, ScreenUtil.dp2px(getActivity(), textPadding), 0);
            return convertView;
        }


        @Override
        public Fragment getFragmentForPage(int position) {
            NewsFragment mainFragment = new NewsFragment();
            mainFragment.setPagerBehavior(mPagerBehavior);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }

}
