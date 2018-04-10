package com.cc.carmanager.bean;

import android.text.BoringLayout;

import com.cc.carmanager.widget.SwitchImage;

import java.util.List;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarsConfigListBean {
    private List<CarConfigBean> data;
    private boolean success;

    public List<CarConfigBean> getData() {
        return data;
    }

    public void setData(List<CarConfigBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class CarConfigBean{
        private CarConfig carConfig;
        private CarSeriesBean.CarSerieBean carSeries;

        public CarConfig getCarConfig() {
            return carConfig;
        }

        public void setCarConfig(CarConfig carConfig) {
            this.carConfig = carConfig;
        }

        public CarSeriesBean.CarSerieBean getCarSeries() {
            return carSeries;
        }

        public void setCarSeries(CarSeriesBean.CarSerieBean carSeries) {
            this.carSeries = carSeries;
        }
    }
    public class CarConfig{
        private String airConditionerConfig;
        private String antiTheftConfig;
        private String assistConfig;
        private String basisConfig;
        private String brakingConfig;
        private String carBodyConfig;
        private int carId;
        private int carSeriesId;
        private String chassisConfig;
        private String electromotorConfig;
        private String gearBoxConfig;
        private String glassConfig;
        private int id;
        private String internalConfig;
        private String lightingConfig;
        private String motorConfig;
        private String multimediaConfig;
        private double price;
        private String safetyConfig;
        private String seatConfig;

        public String getAirConditionerConfig() {
            return airConditionerConfig;
        }

        public void setAirConditionerConfig(String airConditionerConfig) {
            this.airConditionerConfig = airConditionerConfig;
        }

        public String getAntiTheftConfig() {
            return antiTheftConfig;
        }

        public void setAntiTheftConfig(String antiTheftConfig) {
            this.antiTheftConfig = antiTheftConfig;
        }

        public String getAssistConfig() {
            return assistConfig;
        }

        public void setAssistConfig(String assistConfig) {
            this.assistConfig = assistConfig;
        }

        public String getBasisConfig() {
            return basisConfig;
        }

        public void setBasisConfig(String basisConfig) {
            this.basisConfig = basisConfig;
        }

        public String getBrakingConfig() {
            return brakingConfig;
        }

        public void setBrakingConfig(String brakingConfig) {
            this.brakingConfig = brakingConfig;
        }

        public String getCarBodyConfig() {
            return carBodyConfig;
        }

        public void setCarBodyConfig(String carBodyConfig) {
            this.carBodyConfig = carBodyConfig;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getCarSeriesId() {
            return carSeriesId;
        }

        public void setCarSeriesId(int carSeriesId) {
            this.carSeriesId = carSeriesId;
        }

        public String getChassisConfig() {
            return chassisConfig;
        }

        public void setChassisConfig(String chassisConfig) {
            this.chassisConfig = chassisConfig;
        }

        public String getElectromotorConfig() {
            return electromotorConfig;
        }

        public void setElectromotorConfig(String electromotorConfig) {
            this.electromotorConfig = electromotorConfig;
        }

        public String getGearBoxConfig() {
            return gearBoxConfig;
        }

        public void setGearBoxConfig(String gearBoxConfig) {
            this.gearBoxConfig = gearBoxConfig;
        }

        public String getGlassConfig() {
            return glassConfig;
        }

        public void setGlassConfig(String glassConfig) {
            this.glassConfig = glassConfig;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInternalConfig() {
            return internalConfig;
        }

        public void setInternalConfig(String internalConfig) {
            this.internalConfig = internalConfig;
        }

        public String getLightingConfig() {
            return lightingConfig;
        }

        public void setLightingConfig(String lightingConfig) {
            this.lightingConfig = lightingConfig;
        }

        public String getMotorConfig() {
            return motorConfig;
        }

        public void setMotorConfig(String motorConfig) {
            this.motorConfig = motorConfig;
        }

        public String getMultimediaConfig() {
            return multimediaConfig;
        }

        public void setMultimediaConfig(String multimediaConfig) {
            this.multimediaConfig = multimediaConfig;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSafetyConfig() {
            return safetyConfig;
        }

        public void setSafetyConfig(String safetyConfig) {
            this.safetyConfig = safetyConfig;
        }

        public String getSeatConfig() {
            return seatConfig;
        }

        public void setSeatConfig(String seatConfig) {
            this.seatConfig = seatConfig;
        }

        public String getTypeValue(String type){
            switch (type){
                case "基本参数":
                    return getBasisConfig();
                case "车身":
                    return getCarBodyConfig();
                case "发动机":
                    return getMotorConfig();
                case "电动机":
                    return getElectromotorConfig();
                case "变速箱":
                    return getGearBoxConfig();
                case "底盘转向":
                    return getChassisConfig();
                case "车轮制动":
                    return getBrakingConfig();
                case "主/被动安全装备":
                    return getSafetyConfig();
                case "辅助/操控配置":
                    return getAssistConfig();
                case "外部/防盗配置":
                    return getAntiTheftConfig();
                case "内部配置":
                    return getInternalConfig();
                case "座椅配置":
                    return getSeatConfig();
                case "多媒体配置":
                    return getMultimediaConfig();
                case "灯光配置":
                    return getLightingConfig();
                case "玻璃/后视镜":
                    return  getGlassConfig();
                case "空调/冰箱":
                    return getAirConditionerConfig();
                default:
                    return "";
            }
        }
    }
}
