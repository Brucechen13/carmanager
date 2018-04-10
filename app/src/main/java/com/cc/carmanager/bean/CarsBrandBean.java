package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2017/11/25.
 */

public class CarsBrandBean {

    private List<CarBrandBean> data;
    public boolean success;

    public List<CarBrandBean> getData() {
        return data;
    }

    public void setData(List<CarBrandBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class CarBrandBean{
        String brandName;
        String iconSrc;
        int id;
        int idx;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getIconSrc() {
            return iconSrc;
        }

        public void setIconSrc(String iconSrc) {
            this.iconSrc = iconSrc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }
    }

}
