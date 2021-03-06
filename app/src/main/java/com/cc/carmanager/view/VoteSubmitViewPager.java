package com.cc.carmanager.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenc on 2018/4/8.
 */

public class VoteSubmitViewPager extends ViewPager {

    private boolean isScrollable = false;

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public VoteSubmitViewPager(Context context) {
        super(context);

    }

    public VoteSubmitViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //滑动事件
        if (!isScrollable)
            return false;
        return super.onTouchEvent(arg0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //点击事件
        if (!isScrollable)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }

}