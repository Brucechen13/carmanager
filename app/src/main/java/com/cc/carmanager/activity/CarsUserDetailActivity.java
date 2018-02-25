package com.cc.carmanager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.fragment.cars.CarsConfigFragment;
import com.cc.carmanager.fragment.cars.CarsUserFitFragment;
import com.cc.carmanager.fragment.cars.CarsUserFunctionFragment;
import com.cc.carmanager.util.ScreenUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;

/**
 * Created by chenc on 2017/12/31.
 */

public class CarsUserDetailActivity extends BarBaseActivity {
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
        setContentView(R.layout.activity_cars_detail);

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.fragment_tabmain_indicator);
        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(this, barWidth));
        indicator.setScrollBar(colorBar);

        viewPager.setOffscreenPageLimit(4);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private String[] tabNames = {"车辆功能", "配置参数", "保养事项"};

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
            if (position == 0) {
                mainFragment = new CarsUserFunctionFragment();
            } else if (position == 2) {
                mainFragment = new CarsUserFitFragment();
            } else if (position == 1) {
                mainFragment = new CarsConfigFragment();
            }
            return mainFragment;
        }
    }
}