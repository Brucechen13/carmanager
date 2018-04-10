package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2017/10/26.
 */
public class BrandsItemBean{
    private List<BrandListBean> data;
    private boolean success;

    public List<BrandListBean> getData() {
        return data;
    }

    public void setData(List<BrandListBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}