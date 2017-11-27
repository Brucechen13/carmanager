package com.cc.carmanager.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.fragment.CarsFragment;
import com.cc.carmanager.fragment.HomeFragment;
import com.cc.carmanager.fragment.MapsFragment;
import com.cc.carmanager.fragment.MaterialFragment;
import com.cc.carmanager.fragment.ProfileFragment;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends AppCompatActivity {

    private IndicatorViewPager indicatorViewPager;
    //private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.content_main);

        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(R.string.index_name);
//        setSupportActionBar(toolbar);
        invalidateOptionsMenu();
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintColor(getResources().getColor(R.color.tab_top_background));
//            tintManager.setStatusBarTintEnabled(true);
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"首页", "汽车", "充电", "资料", "我的"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector, R.drawable.maintab_5_selector};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_tab_content);
            textView.setText(tabNames[position]);

            ImageView image = (ImageView) convertView.findViewById(R.id.iv_tab_img);
            image.setImageResource(tabIcons[position]);
            return convertView;
        }
        public Fragment mainFragment;

        @Override
        public Fragment getFragmentForPage(int position) {
            if (position == 0) {
                mainFragment = new HomeFragment();
            } else if(position == 1){
                mainFragment = new CarsFragment();
            }else if(position == 2){
                mainFragment = new MapsFragment();
            }else if(position == 3) {
                mainFragment = new MaterialFragment();
            }else{
                mainFragment = new ProfileFragment();
            }
            return mainFragment;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("main", "main" + requestCode);
            Fragment fragment = ((IndicatorViewPager.IndicatorFragmentPagerAdapter)
                    (indicatorViewPager.getAdapter())).getCurrentFragment();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
