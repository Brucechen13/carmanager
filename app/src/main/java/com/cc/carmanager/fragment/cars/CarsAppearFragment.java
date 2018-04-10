package com.cc.carmanager.fragment.cars;

import android.os.Bundle;
import android.util.Log;

import com.cc.carmanager.R;
import com.cc.carmanager.bean.CarPicBean;
import com.cc.carmanager.bean.CarPicListBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2017/11/12.
 */

public class CarsAppearFragment extends LazyFragment {

    private NineGridView[] nineGrids = new NineGridView[4];
    List<List<ImageInfo>> imageInfos = new ArrayList<>();
    private int size = 4;
    private int carId;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carsappear_fragment_layout);
        nineGrids[0] = (NineGridView) findViewById(R.id.nineGrid0);
        nineGrids[1] = (NineGridView) findViewById(R.id.nineGrid1);
        nineGrids[2] = (NineGridView) findViewById(R.id.nineGrid2);
        nineGrids[3] = (NineGridView) findViewById(R.id.nineGrid3);

        Bundle bundle = getArguments();
        carId = bundle.getInt("carId");

        initData();
    }

    private void initData() {
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_CAR_PIC, 9);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarPicListBean mRecommendBean = gson.fromJson(resultStr, CarPicListBean.class);
                if (mRecommendBean.isSuccess()) {
                    for(CarPicBean bean : mRecommendBean.getData()){
                        for(int i = 0; i < size; i ++){
                            imageInfos.add(new ArrayList<ImageInfo>());
                            Gson gson2 = new Gson();
                            java.lang.reflect.Type type = new TypeToken<ArrayList<String>>(){}.getType();
                            String json_str = bean.getTypeVal(i);
                            List<String> jsonMap = gson2.fromJson(json_str, type);
                            if(jsonMap != null) {
                                for (String pic : jsonMap) {
                                    pic = "http://img.pcauto.com.cn/images/pcautogallery/modle/article/201710/29/15092917392405740_660.webp";
                                    ImageInfo info = new ImageInfo();
                                    info.setThumbnailUrl(pic);
                                    info.setBigImageUrl(pic);
                                    imageInfos.get(i).add(info);
                                }
                            }
                        }
                    }
                    initGridView();
                } else {
                    ToastUtils.makeShortText("未查询到车型图片", CarsAppearFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("car", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }

    private void initGridView() {
        for(int i = 0; i < size; i ++) {
            nineGrids[i].setAdapter(new NineGridViewClickAdapter(this.getActivity(), imageInfos.get(i)));

            if (imageInfos.get(i) != null && imageInfos.get(i).size() == 1) {
                nineGrids[i].setSingleImageRatio(1);
            }
        }
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

}