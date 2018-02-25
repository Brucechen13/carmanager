package com.cc.carmanager.fragment.cars;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.util.URLImageParser;
import com.cc.carmanager.widget.FilterView;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenc on 2017/12/31.
 */

public class CarsUserFunctionFragment extends LazyFragment implements FilterView.OnFilterItemClickListener{
    //private SystemBarTintManager tintManager;
    private Context context;
    private TextView content;
    private TextView title;
    private TextView authorAndTime;
    private String link;
    private final String template = "<p><img src='LINK'/></p>";
    private List<String> mData;
    private FilterView mFilterView;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cars_userfunc_fragment);

        String html = "Hello \n" +
                "<img src='http://ww1.sinaimg.cn/mw600/4dc7b570jw1drn1o8mrp0j.jpg' />" +
                " This is a test \n" +
                "<img src='http://att.bbs.duowan.com/forum/201311/01/0950172al0qkazlh20hh9n.png'/>";

        String htmlTest = "<p>　　在众多安卓手机中，Nexus系列一贯被视为Google的“亲儿子”，但其实只有设计来自Google，代工生产还是交给其他厂商，包括LG、HTC、三星、华为、摩托罗拉等等。<\\/p>" +
                "<p>　　不过有传闻称，Google打算完全自己玩儿了，因为一则iPhone在高端市场上不断蚕食市场份额，二则Nexus现在本身的表现也越来越不好：销售渠道过于狭窄，缺乏运营商合作，新的Nexus 6P/5X定位太高影响销售……<\\/p>" +
                "<p>　　Google CEO Sundar Pichai已经向员工和一些外部人士透露，计划将Nexus系列完全掌控在自己手中，从设计到生产都一手负责，不再依赖其他手机厂商，就像Pixel C笔记本那样变成纯粹的Google产品。<\\/p>" +
                "<p>　　这样一来，Nexus设备也不会再冠以其他厂商的牌子，只打Google自己的标识。<\\/p>" +
                "<p>　　虽然Google没有透露该计划的具体细节和执行时间，但是据了解，HTC内部人士对于Google的这种做法并不意外，HTC也可能成为最后一个代工Nexus的第三方厂商。<\\/p>" +
                "<p>　　此前有消息称，HTC今年将独自代工两款Nexus手机，分别为5.0英寸、5.5英寸。<\\/p><!--IMG#0-->";

        String body = htmlTest.replace("<!--IMG#0-->", template.replace("LINK", "http://img1.cache.netease.com/catchpic/5/59/59F9EB30B047D22DAD5F12B14DB4682E.jpg"));


        content = (TextView) findViewById(R.id.tv_content);
        title = (TextView) findViewById(R.id.tv_newstitle);
        authorAndTime = (TextView) findViewById(R.id.tv_author_time);

        URLImageParser p = new URLImageParser(content, this.getActivity());
        Spanned htmlSpan = Html.fromHtml(body, p, null);
        content.setText(htmlSpan);

        mFilterView = (FilterView) findViewById(R.id.filterView);
        this.initData();

    }

    private void initData() {
        mData = new ArrayList<>();
        mData.addAll(Arrays.asList("今天","前7天","后7天"));
        mFilterView.setFilterData(mData);
        mFilterView.setOnFilterItemClickListener(this);
    }

    @Override
    public void onFilterItemClick(String content) {

    }
}