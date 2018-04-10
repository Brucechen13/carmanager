package com.cc.carmanager.bean;

import android.text.BoringLayout;

/**
 * Created by chenc on 2018/3/23.
 */

public class PostStatusBean {
    private String mess;
    private boolean success;

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
