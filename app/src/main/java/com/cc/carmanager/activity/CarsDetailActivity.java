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
import com.cc.carmanager.fragment.cars.CarsAppearFragment;
import com.cc.carmanager.fragment.cars.CarsConfigFragment;
import com.cc.carmanager.fragment.cars.CarsIntroFragment;
import com.cc.carmanager.fragment.cars.CarsCompanyFragment;
import com.cc.carmanager.util.ScreenUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;

/**
 * Created by chenc on 2017/11/11.
 */
public class CarsDetailActivity extends BarBaseActivity {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private int carId;
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


        Bundle bundle = getIntent().getExtras();
        carId = bundle.getInt("carId");//读出数据

        setHeader("车辆详情");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("cccc", "Fragment 所在的Activity onDestroy " + this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private String[] tabNames = {"综述", "配置", "图片", "经销商", "维修商"};

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
            if(position == 0) {
                mainFragment = new CarsIntroFragment();
            }else if(position == 2){
                mainFragment = new CarsAppearFragment();
            }else if(position == 1){
                mainFragment = new CarsConfigFragment();
            }else{
                mainFragment = new CarsCompanyFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putInt("carId", carId);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }
}