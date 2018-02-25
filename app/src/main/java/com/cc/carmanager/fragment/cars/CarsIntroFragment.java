package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
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
import com.cc.carmanager.vollley.MySingleton;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsIntroFragment extends LazyFragment{

    private List<Fragment> xinwen_framentlist;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carintro_fragment_layout);

        NetworkImageView networkImageView=(NetworkImageView)findViewById(R.id.iv_left_image);
        networkImageView.setDefaultImageResId(R.drawable.load_fail);
        networkImageView.setErrorImageResId(R.drawable.load_fail);
        networkImageView.setImageUrl("http://img.pcauto.com.cn/images/pcautogallery/modle/article/201710/29/15092917392405740_660.webp",
                MySingleton.getInstance().getImageLoader());

        //新闻导航栏控件
        final RadioGroup xinwen_Rradio = (RadioGroup) findViewById(R.id.xinwen_radiogroup);
        initdata(xinwen_Rradio);
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
                if(checkedId >= 0 && checkedId <= titles_.length) {
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

    String [] titles_ = {"全部在售", "2018款", "2017款", "2016款", "2015款", "2014款"};
    //初始化数据
    private void initdata(RadioGroup group) {
        int size = titles_.length;
        for (int i = 0; i < size; i++) {
            addRadioButton(group, titles_[i], i==0, i);
        }
    }


    private void addRadioButton(RadioGroup group, String text, boolean checked, int id){
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
        bundletoutiao.putString("xinwendaohang", "头条");
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
}
