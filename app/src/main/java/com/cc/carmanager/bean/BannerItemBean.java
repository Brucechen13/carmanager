package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/3.
 */

public class BannerItemBean {
    public String summary;
    public TimeBean publishTime;
    public String imageSrc;
    public int id;
    public String title;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public TimeBean getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(TimeBean publishTime) {
        this.publishTime = publishTime;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
