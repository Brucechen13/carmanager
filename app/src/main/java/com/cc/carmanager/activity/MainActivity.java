package com.cc.carmanager.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import com.cc.carmanager.fragment.MapFragment;
import com.cc.carmanager.fragment.MaterialFragment;
import com.cc.carmanager.fragment.ProfileFragment;
import com.cc.carmanager.util.ToastUtils;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public class MainActivity extends AppCompatActivity {

    private IndicatorViewPager indicatorViewPager;
    //private SystemBarTintManager tintManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;

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


        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            }else {
                ToastUtils.makeShortText("权限已申请", this);
            }
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtils.makeShortText("权限已申请", this);
            } else {
                ToastUtils.makeShortText("用户关闭权限申请", this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                mainFragment = new MapFragment();
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
