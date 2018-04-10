package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class ConsumerAskListBean {

    private List<ConsumerAskItemBean> data;
    private boolean success;

    public List<ConsumerAskItemBean> getData() {
        return data;
    }

    public void setData(List<ConsumerAskItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class ConsumerAskItemBean{
        private TimeBean addTime;
        private String content;
        private int id;
        private int isReplay;
        private int isShow;
        private String replay;
        private TimeBean replayTime;
        private String userName;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsReplay() {
            return isReplay;
        }

        public void setIsReplay(int isReplay) {
            this.isReplay = isReplay;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public String getReplay() {
            return replay;
        }

        public void setReplay(String replay) {
            this.replay = replay;
        }

        public TimeBean getReplayTime() {
            return replayTime;
        }

        public void setReplayTime(TimeBean replayTime) {
            this.replayTime = replayTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
