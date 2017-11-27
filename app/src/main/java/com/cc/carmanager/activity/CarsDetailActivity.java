package com.cc.carmanager.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.fragment.CarsFragment;
import com.cc.carmanager.fragment.HomeFragment;
import com.cc.carmanager.fragment.MapsFragment;
import com.cc.carmanager.fragment.MaterialFragment;
import com.cc.carmanager.fragment.NewsFragment;
import com.cc.carmanager.fragment.ProfileFragment;
import com.cc.carmanager.fragment.cars.CarsAppearFragment;
import com.cc.carmanager.fragment.cars.CarsConfigFragment;
import com.cc.carmanager.util.ScreenUtil;
import com.google.gson.Gson;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.viewpager.SViewPager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/11/11.
 */
public class CarsDetailActivity extends AppCompatActivity {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private int index;
    private final int textPadding = 5;//dp
    private final int barWidth = 42;//dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment_layout);
        Resources res = getResources();

//		tabName = bundle.getString(INTENT_STRING_TABNAME);
        //index = bundle.getInt(INTENT_INT_INDEX);

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.fragment_tabmain_indicator);
        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(this, barWidth));
        indicator.setScrollBar(colorBar);

        viewPager.setOffscreenPageLimit(4);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        Log.d("cccc", "Fragment 将要创建View " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private String[] tabNames = {"综述", "配置", "图片", "经销商"};

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
            textView.setPadding(ScreenUtil.dp2px(getApplicationContext(), textPadding), 0, ScreenUtil.dp2px(getApplicationContext(), textPadding), 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment mainFragment = null;
            if(position == 1){
                mainFragment = new CarsAppearFragment();
            }else{
                mainFragment = new CarsConfigFragment();
            }
//            Bundle bundle = new Bundle();
//            bundle.putString(SecondLayerFragment.INTENT_STRING_TABNAME, URLs.tabName[position]);
//            bundle.putInt(SecondLayerFragment.INTENT_INT_POSITION, position);
//            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }
}