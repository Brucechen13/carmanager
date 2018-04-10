package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarPicBannerBean {
    private CarPicBean data;
    private boolean success;

    public CarPicBean getData() {
        return data;
    }

    public void setData(CarPicBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
