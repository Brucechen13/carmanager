package com.cc.carmanager.bean;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsCompanyBean {
    private List<CarCompanyBean> data;
    private boolean success;

    public List<CarCompanyBean> getData() {
        return data;
    }

    public void setData(List<CarCompanyBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class CarCompanyBean{
        public String carsId;
        private String city;
        private int id;
        private double latitude;
        private String location;
        private double longitude;
        private String province;
        private String salesName;
        private String tel;

        public String getCarsId() {
            return carsId;
        }

        public void setCarsId(String carsId) {
            this.carsId = carsId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSalesName() {
            return salesName;
        }

        public void setSalesName(String salesName) {
            this.salesName = salesName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
