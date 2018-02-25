package com.cc.carmanager.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.adapt.OrderFilterAdapter;

import java.util.List;

/**
 * Created by chenc on 2017/12/31.
 */

public class FilterView extends LinearLayout implements View.OnClickListener,AdapterView.OnItemClickListener{
    // 今天、前一周、后一周
    private LinearLayout llFilter;
    //订单日期、类型、状态文字
    private TextView tvFilterDate;
    //订单日期、类型、状态图标
    private ImageView ivFilterDate;
    //菜单滑出时底部背景图
    private View maskView;
    private ListView lvMenu;
    //日期列表 item所在的位置
    // 当前选中的tab
    private int currentTabIndex = -1;
    private int currentDateItemIndex;
    private Context mContext;
    private List<String> filterData;
    private OrderFilterAdapter mAdapter;
    private boolean isMenuShow;//下拉列表是否显示
    private int menuHeight;//下拉列表的高度
    private OnFilterItemClickListener onFilterItemClickListener;

    //自定义属性
    private boolean filterSearch;
    public FilterView(Context context) {
        this(context,null);
    }

    public FilterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        getAttrs(context, attrs);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        this.init(context);
    }

    /**
     * 得到属性值
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.filterViewAttr);
        filterSearch = ta.getBoolean(R.styleable.filterViewAttr_filterSearch, false);
        ta.recycle();
    }

    private void init(Context context) {
        this.mContext = context;
        View view = null;
        if(filterSearch){
            view = LayoutInflater.from(context).inflate(R.layout.item_filter_search, this);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_filter_cartab, this);
        }
        this.initView(view);
    }
    private void initView(View view) {
        llFilter = (LinearLayout) view.findViewById(R.id.ll_tab);

        tvFilterDate = (TextView) view.findViewById(R.id.tv_tab_order);
        ivFilterDate = (ImageView) view.findViewById(R.id.iv_tab_order);

        maskView = view.findViewById(R.id.view_mask_bg);
        lvMenu = (ListView) view.findViewById(R.id.lv_menu);

        llFilter.setOnClickListener(this);
        maskView.setOnClickListener(this);
        lvMenu.setOnItemClickListener(this);
    }
    // 设置筛选数据
    public void setFilterData(List<String> filterData) {
        this.filterData = filterData;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_tab:
                currentTabIndex = 0;
                setFilterDateAdapter();
                //显示下拉列表
                showMenu();
                break;
            case R.id.view_mask_bg://点击灰色部分
                hideMenu();
                break;
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String content = null;
        switch (currentTabIndex){
            case 0:
                currentDateItemIndex = position;
                content = filterData.get(position);
                setTabTitle(tvFilterDate, content);
                break;//第一个tab
        }
        if (onFilterItemClickListener != null) {
            onFilterItemClickListener.onFilterItemClick(content);//回调，将item值传出
        }
        hideMenu();
    }
    public void setTabTitle(TextView tvTabTitle,String title){
        tvTabTitle.setText(title);
    }

    /**
     * 显示下拉列表项
     */
    public void showMenu() {
        if(isMenuShow) return;
        isMenuShow = true;
        maskView.setVisibility(VISIBLE);
        lvMenu.setVisibility(View.VISIBLE);
        lvMenu.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                lvMenu.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                menuHeight = lvMenu.getHeight();
                ObjectAnimator.ofFloat(lvMenu, "translationY", -menuHeight, 0).setDuration(200).start();
            }
        });
    }

    /**
     * 隐藏下拉列表项
     */
    public void hideMenu(){
        if(!isMenuShow) return;
        isMenuShow = false;
        maskView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(lvMenu, "translationY", 0, -menuHeight).setDuration(200).start();
        if(currentTabIndex == 0){
            ivFilterDate.setImageResource(R.mipmap.ic_order_down_select);
        }
        lvMenu.setVisibility(View.GONE);
    }

    private void setFilterDateAdapter() {
        tvFilterDate.setTextColor(mContext.getResources().getColor(R.color.blueText));
        ivFilterDate.setImageResource(R.mipmap.ic_order_up_select);
        mAdapter = new OrderFilterAdapter(mContext, filterData,currentDateItemIndex);
        lvMenu.setAdapter(mAdapter);
    }

    public interface OnFilterItemClickListener {
        void onFilterItemClick(String content);
    }
    public void setOnFilterItemClickListener(OnFilterItemClickListener onFilterClickListener) {
        this.onFilterItemClickListener = onFilterClickListener;
    }


}

