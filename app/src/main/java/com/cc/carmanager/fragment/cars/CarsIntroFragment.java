package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.bean.CarPicBannerBean;
import com.cc.carmanager.bean.CarPicBean;
import com.cc.carmanager.bean.CarSeriesBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.vollley.MySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsIntroFragment extends LazyFragment{

    private List<Fragment> xinwen_framentlist;
    int carId;
    private List<YearList.YearType> datas = new ArrayList<>();
    private RadioGroup xinwen_Rradio;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carintro_fragment_layout);

        Bundle bundle = getArguments();
        carId = bundle.getInt("carId");

        xinwen_Rradio = (RadioGroup) findViewById(R.id.xinwen_radiogroup);
        initdata(xinwen_Rradio);
        initBanner();


    }

    //初始化banner
    private void initBanner(){
        final NetworkImageView networkImageView=(NetworkImageView)findViewById(R.id.iv_left_image);
        networkImageView.setDefaultImageResId(R.drawable.load_fail);
        networkImageView.setErrorImageResId(R.drawable.load_fail);

        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_CAR_PICBANNER, 9);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarPicBannerBean mRecommendBean = gson.fromJson(resultStr, CarPicBannerBean.class);
                if (mRecommendBean.isSuccess()) {
                    Gson gson2 = new Gson();
                    java.lang.reflect.Type type = new TypeToken<ArrayList<String>>(){}.getType();
                    String json_str = mRecommendBean.getData().getAppearance();
                    List<String> jsonMap = gson2.fromJson(json_str, type);
                    if(jsonMap != null && jsonMap.size() > 0) {
                        String pic = jsonMap.get(0);
                        pic = "http://img.pcauto.com.cn/images/pcautogallery/modle/article/201710/29/15092917392405740_660.webp";
                        networkImageView.setImageUrl(pic,
                                MySingleton.getInstance().getImageLoader());
                    }
                } else {
                    ToastUtils.makeShortText("未查询到车型图片", CarsIntroFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("car", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    //初始化导航栏
    private void initRadioGroup(){//新闻导航栏控件
        final ViewPager xinwen_viewpage = (ViewPager) findViewById(R.id.xinwen_viewpager);
        final HorizontalScrollView xinwen_scrollView = (HorizontalScrollView) findViewById(R.id.xinwen_scroll);
        final TextView xinwen_indicator = (TextView) findViewById(R.id.xinwen_indicator);
        //新闻页面的adapter
        XinWenFragmentAdapter fragmentAdapter = new XinWenFragmentAdapter(getActivity().getSupportFragmentManager());
        xinwen_viewpage.setAdapter(fragmentAdapter);
        xinwen_viewpage.setOffscreenPageLimit(2);

        xinwen_Rradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                //遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }
                if(checkedId >= 0 && checkedId <= datas.size()+1) {
                    xinwen_viewpage.setCurrentItem(checkedId);
                }
            }
        });

        xinwen_viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // 获取对应位置的RadioButton
                RadioButton radioBtn = (RadioButton) xinwen_Rradio.getChildAt(arg0);
                // 设置对应位置的RadioButton为选中的状态
                radioBtn.setChecked(true);
                /* 滚动HorizontalScrollView使选中的RadioButton处于屏幕中间位置 */
                //获取屏幕信息
                DisplayMetrics outMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                //获取每一个RadioButton的宽度
                int radioBtnPiexls = radioBtn.getWidth();
                //计算滚动距离
                int distance = (int) ((arg0 + 0.5) * radioBtnPiexls - outMetrics.widthPixels / 2);
                //滚动
                xinwen_scrollView.scrollTo(distance, 0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
				/* 伴随着ViewPager的滑动，滚动指示条TextView */
                // 获取TextView在其父容器LinearLayout中的布局参数
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) xinwen_indicator
                        .getLayoutParams();
                params.leftMargin = (int) ((arg0 + arg1) * params.width);
                xinwen_indicator.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    //初始化数据
    private void initdata(final RadioGroup group) {
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_CAR_YEARTYPE, carId);
        addRadioButton(group, "全部在售", true, 0, 0);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                YearList mRecommendBean = gson.fromJson(resultStr, YearList.class);
                if (mRecommendBean.isSuccess()) {
                    int index = 1;
                    datas.addAll(mRecommendBean.getData());
                    for(YearList.YearType bean : mRecommendBean.getData()){
                        String key = bean.getYearType()+"款";
                        addRadioButton(group, key, false, index++, bean.getYearType());
                    }
                    initRadioGroup();
                } else {
                    ToastUtils.makeShortText("未查询到车型", CarsIntroFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }


    private void addRadioButton(RadioGroup group, String text, boolean checked, int id, int yearType){
        RadioButton radioButton = (RadioButton) LayoutInflater.from(this.getActivity()).inflate(R.layout.item_radiobutton, null);
        radioButton.setText(text);
        radioButton.setChecked(checked);
        radioButton.setId(id);
        group.addView(radioButton);//将单选按钮添加到RadioGroup中

        if(xinwen_framentlist == null) {
            xinwen_framentlist = new ArrayList<>();
        }
        CarsIntroListFragment toutiao=new CarsIntroListFragment();
        Bundle bundletoutiao=new Bundle();
        bundletoutiao.putInt("yearType", yearType);
        bundletoutiao.putInt("carId", carId);
        toutiao.setArguments(bundletoutiao);
        xinwen_framentlist.add(toutiao);
    }

    //viewpager的填充类
    class XinWenFragmentAdapter extends FragmentPagerAdapter {
        public XinWenFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return xinwen_framentlist.get(arg0);
        }

        @Override
        public int getCount() {
            return xinwen_framentlist.size();
        }
    }

    class YearList{
        private List<YearType> data;
        private boolean success;

        public List<YearType> getData() {
            return data;
        }

        public void setData(List<YearType> data) {
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
        class YearType{
            private int yearType;

            public int getYearType() {
                return yearType;
            }

            public void setYearType(int yearType) {
                this.yearType = yearType;
            }
        }
    }
}
