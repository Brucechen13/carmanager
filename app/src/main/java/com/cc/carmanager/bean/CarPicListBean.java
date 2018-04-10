package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarPicListBean {
    private List<CarPicBean> data;
    private boolean success;

    public List<CarPicBean> getData() {
        return data;
    }

    public void setData(List<CarPicBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
