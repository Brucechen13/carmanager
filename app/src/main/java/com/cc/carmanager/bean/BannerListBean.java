package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/3.
 */

public class BannerListBean {

    private List<BannerItemBean> data;
    public boolean success;

    public List<BannerItemBean> getData() {
        return data;
    }

    public void setData(List<BannerItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
