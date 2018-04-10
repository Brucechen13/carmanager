package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/3.
 */

public class NewsFullBean {
    public NewsContentBean data;
    public boolean success;

    public NewsContentBean getData() {
        return data;
    }

    public void setData(NewsContentBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
