package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/3.
 */

public class NewsListBean {

    private List<NewsItemBean> data;
    public boolean success;

    public List<NewsItemBean> getData() {
        return data;
    }

    public void setData(List<NewsItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
