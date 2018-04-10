package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/3.
 */

public class NewsItemBean {
    private String summary;
    private TimeBean publishTime;
    private String iconSrc;
    private int npNum;
    private int visitNum;
    private int id;
    private String title;
    private int isRecommend;
    private int isUp;
    private int isHot;

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

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

    public String getIconSrc() {
        return iconSrc;
    }

    public void setIconSrc(String iconSrc) {
        this.iconSrc = iconSrc;
    }

    public int getNpNum() {
        return npNum;
    }

    public void setNpNum(int npNum) {
        this.npNum = npNum;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
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
