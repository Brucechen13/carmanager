package com.cc.carmanager.bean;

/**
 * Created by chenc on 2018/3/3.
 */

public class CommentsItemBean {
    public int id;
    public String replay;
    public String userName;
    public TimeBean addTime;
    public String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TimeBean getAddTime() {
        return addTime;
    }

    public void setAddTime(TimeBean addTime) {
        this.addTime = addTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
