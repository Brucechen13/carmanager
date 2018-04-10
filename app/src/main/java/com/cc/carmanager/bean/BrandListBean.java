package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/21.
 */

public class BrandListBean {

    private List<BrandCarBean> data;
    private boolean success;

    public List<BrandCarBean> getData() {
        return data;
    }

    public void setData(List<BrandCarBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class BrandCarBean{
        private int id;
        private double maxPrice;
        private String carName;
        private String iconSrc;
        private double minPrice;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getIconSrc() {
            return iconSrc;
        }

        public void setIconSrc(String iconSrc) {
            this.iconSrc = iconSrc;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
