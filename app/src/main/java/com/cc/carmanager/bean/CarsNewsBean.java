package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2017/11/25.
 */

public class CarsNewsBean {
    private int returncode;
    private String message;

    private List<FocusimgBean> focusimg;
    private List<NewslistBean> newslist;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FocusimgBean> getFocusimg() {
        return focusimg;
    }

    public void setFocusimg(List<FocusimgBean> focusimg) {
        this.focusimg = focusimg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class FocusimgBean {
        private String updatetime;
        private int id;
        private String imgurl;
        private String title;
        private String type;
        private int replycount;
        private int pageindex;
        private int JumpType;
        private String jumpurl;
        private int mediatype;
        private int fromtype;

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getReplycount() {
            return replycount;
        }

        public void setReplycount(int replycount) {
            this.replycount = replycount;
        }

        public int getPageindex() {
            return pageindex;
        }

        public void setPageindex(int pageindex) {
            this.pageindex = pageindex;
        }

        public int getJumpType() {
            return JumpType;
        }

        public void setJumpType(int JumpType) {
            this.JumpType = JumpType;
        }

        public String getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(String jumpurl) {
            this.jumpurl = jumpurl;
        }

        public int getMediatype() {
            return mediatype;
        }

        public void setMediatype(int mediatype) {
            this.mediatype = mediatype;
        }

        public int getFromtype() {
            return fromtype;
        }

        public void setFromtype(int fromtype) {
            this.fromtype = fromtype;
        }
    }

    public static class NewslistBean {
        private int id;
        private String title;
        private int mediatype;
        private String type;
        private String time;
        private String indexdetail;
        private String smallpic;
        private int replycount;
        private int pagecount;
        private int jumppage;
        private String lasttime;
        private int newstype;
        private String updatetime;

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

        public int getMediatype() {
            return mediatype;
        }

        public void setMediatype(int mediatype) {
            this.mediatype = mediatype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIndexdetail() {
            return indexdetail;
        }

        public void setIndexdetail(String indexdetail) {
            this.indexdetail = indexdetail;
        }

        public String getSmallpic() {
            return smallpic;
        }

        public void setSmallpic(String smallpic) {
            this.smallpic = smallpic;
        }

        public int getReplycount() {
            return replycount;
        }

        public void setReplycount(int replycount) {
            this.replycount = replycount;
        }

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
        }

        public int getJumppage() {
            return jumppage;
        }

        public void setJumppage(int jumppage) {
            this.jumppage = jumppage;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public int getNewstype() {
            return newstype;
        }

        public void setNewstype(int newstype) {
            this.newstype = newstype;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }
    }
}
