package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/21.
 */

public class CarSeriesBean {
    private List<CarSerieBean> data;
    private boolean success;

    public List<CarSerieBean> getData() {
        return data;
    }

    public void setData(List<CarSerieBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class CarSerieBean{
        private int brandId;
        private int carId;
        private int id;
        private int idx;
        private int isSale;
        private double price;
        private String seriesName;
        private int yearType;

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
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

        public int isSale() {
            return isSale;
        }

        public void setSale(int sale) {
            isSale = sale;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }

        public int getYearType() {
            return yearType;
        }

        public void setYearType(int yearType) {
            this.yearType = yearType;
        }
    }
}
