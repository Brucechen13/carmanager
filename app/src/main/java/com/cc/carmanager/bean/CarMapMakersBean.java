package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarMapMakersBean {
    private List<CarMapMakerBean> data;
    private boolean success;

    public List<CarMapMakerBean> getData() {
        return data;
    }

    public void setData(List<CarMapMakerBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public class CarMapMakerBean {
        private String city;
        private int id;
        private double latitude;
        private String location;
        private double longitude;
        private String openGrade;
        private String province;
        private String useType;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getOpenGrade() {
            return openGrade;
        }

        public void setOpenGrade(String openGrade) {
            this.openGrade = openGrade;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUseType() {
            return useType;
        }

        public void setUseType(String useType) {
            this.useType = useType;
        }
    }
}
