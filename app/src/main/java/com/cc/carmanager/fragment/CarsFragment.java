package com.cc.carmanager.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.util.LoginHelper;
import com.cc.carmanager.util.ScreenUtil;
import com.cc.carmanager.util.ToastUtils;
import com.shizhefei.fragment.LazyFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chenc on 2017/10/27.
 */
public class CarsFragment extends LazyFragment {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    private final int textPadding = 5;//dp
    private final int barWidth = 42;//dp

    private String searchText;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.cars_fragment_layout);
        Resources res = getResources();

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_tabmain_viewPager);
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.fragment_tabmain_indicator);
        ColorBar colorBar = new ColorBar(getApplicationContext(), Color.RED, 5);
        colorBar.setWidth(ScreenUtil.dp2px(getActivity(), barWidth));
        indicator.setScrollBar(colorBar);

        viewPager.setOffscreenPageLimit(2);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        final EditText editText = (EditText)findViewById(R.id.search_input);
        editText.clearFocus();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_GO){
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = editText.getText().toString();
                    try {
                        searchText = URLEncoder.encode(searchText, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e("car", "index"+indicatorViewPager.getCurrentItem());
                    if(indicatorViewPager.getCurrentItem() == 0){
                        brandFragment.setSearchText(searchText.equals("")?null:searchText);
                    }else{
                        userFragment.setSearchText(searchText.equals("")?null:searchText);
                    }
                    Log.e("car", searchText);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
    }

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
    }

    @Override
    protected void onFragmentStopLazy() {
        super.onFragmentStopLazy();
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private CarsBrandFragment brandFragment;
    private CarsUserFragment userFragment;

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private String[] tabNames = {"购车指南", "用车指南"};

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
            Bundle bundle=new Bundle();
            if(position == 0) {
                brandFragment = new CarsBrandFragment();
                return brandFragment;
            }else {
                userFragment = new CarsUserFragment();
                return userFragment;
            }
        }
    }
}
