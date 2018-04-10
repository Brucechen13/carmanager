package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarPicBean {
    private String control;
    private String detail;
    private String seat;
    private String appearance;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getTypeVal(int type){
        return type==0?getAppearance():type==1?getControl():type==2?getSeat():getDetail();
    }
}
