package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarPicSeriesBean {
    private CarPicBean picJson;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CarPicBean getPicJson() {
        return picJson;
    }

    public void setPicJson(CarPicBean picJson) {
        this.picJson = picJson;
    }
}
