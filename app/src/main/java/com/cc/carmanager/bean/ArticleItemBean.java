package com.cc.carmanager.bean;

/**
 * Created by chenc on 2017/11/4.
 */
public class ArticleItemBean {
    private String url;
    private String picUrl;
    private String title;
    private double fileSize;
    private int viewSize;
    private boolean isCost;
    private double costMoney;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public int getViewSize() {
        return viewSize;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    public boolean isCost() {
        return isCost;
    }

    public void setIsCost(boolean isCost) {
        this.isCost = isCost;
    }

    public double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(double costMoney) {
        this.costMoney = costMoney;
    }
}
