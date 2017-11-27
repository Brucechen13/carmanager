package com.cc.carmanager.fragment.cars;

import android.os.Bundle;

import com.cc.carmanager.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/11/12.
 */

public class CarsAppearFragment extends LazyFragment {

    private NineGridView nineGrid;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carsappear_fragment_layout);
        nineGrid = (NineGridView) findViewById(R.id.nineGrid);
        initGridView();
    }

    private void initGridView() {
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl("http://i7.hexunimg.cn/2016-02-23/182377490.jpg");
            info.setBigImageUrl("http://i7.hexunimg.cn/2016-02-23/182377490.jpg");
            imageInfo.add(info);
        }
        nineGrid.setAdapter(new NineGridViewClickAdapter(this.getActivity(), imageInfo));

        if (imageInfo != null && imageInfo.size() == 1) {
            nineGrid.setSingleImageRatio(1);
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