package com.cc.carmanager.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.bean.CarPicBean;
import com.cc.carmanager.bean.CarPicSeriesBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarSeriesPicActivity extends BarBaseActivity {


    private NineGridView[] nineGrids = new NineGridView[4];
    List<List<ImageInfo>> imageInfos = new ArrayList<>();
    private int size = 4;

    private int carId, seriesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_seriespic);

        nineGrids[0] = (NineGridView) findViewById(R.id.nineGrid0);
        nineGrids[1] = (NineGridView) findViewById(R.id.nineGrid1);
        nineGrids[2] = (NineGridView) findViewById(R.id.nineGrid2);
        nineGrids[3] = (NineGridView) findViewById(R.id.nineGrid3);
        initData();
        setHeader("车型配置");
    }

    private void initData() {
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_SERIES_PIC, 9, 30);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarPicSeriesBean mRecommendBean = gson.fromJson(resultStr, CarPicSeriesBean.class);
                if (mRecommendBean.isSuccess()) {
                    for (int i = 0; i < size; i++) {
                        imageInfos.add(new ArrayList<ImageInfo>());
                        Gson gson2 = new Gson();
                        java.lang.reflect.Type type = new TypeToken<ArrayList<String>>() {
                        }.getType();
                        String json_str = mRecommendBean.getPicJson().getTypeVal(i);
                        List<String> jsonMap = gson2.fromJson(json_str, type);
                        if (jsonMap != null) {
                            for (String pic : jsonMap) {
                                pic = "http://img.pcauto.com.cn/images/pcautogallery/modle/article/201710/29/15092917392405740_660.webp";
                                ImageInfo info = new ImageInfo();
                                info.setThumbnailUrl(pic);
                                info.setBigImageUrl(pic);
                                imageInfos.get(i).add(info);
                            }
                        }
                    }
                    initGridView();
                } else {
                    ToastUtils.makeShortText("未查询到车型图片", CarSeriesPicActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("car", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    private void initGridView() {
        for (int i = 0; i < size; i++) {
            nineGrids[i].setAdapter(new NineGridViewClickAdapter(this, imageInfos.get(i)));

            if (imageInfos.get(i) != null && imageInfos.get(i).size() == 1) {
                nineGrids[i].setSingleImageRatio(1);
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}