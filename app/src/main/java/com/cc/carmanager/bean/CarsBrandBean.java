package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2017/11/25.
 */

public class CarsBrandBean {

    private int returncode;
    private String message;
    private List<CarBrandBean> brandlist;

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

    public List<CarBrandBean> getBrandlist() {
        return brandlist;
    }

    public void setBrandlist(List<CarBrandBean> brandlist) {
        this.brandlist = brandlist;
    }

    public class CarBrandBean{
        String letter;
        List<CarBean> list;

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public List<CarBean> getList() {
            return list;
        }

        public void setList(List<CarBean> list) {
            this.list = list;
        }
    }

    public class CarBean{
        String id;
        String name;
        String imgurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
