package com.cc.carmanager.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenc on 2018/3/3.
 */

public class TimeBean {
    public String date;
    public String day;
    public String hours;
    public String minutes;
    public String month;
    public String nanos;
    public String seconds;
    public String time;
    public String timezoneOffset;
    public String year;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNanos() {
        return nanos;
    }

    public void setNanos(String nanos) {
        this.nanos = nanos;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDateTime(){
        String res;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        long  l = Long.valueOf(getTime());
        res = sdf.format(new Date(l));//单位秒
        return res;
    }

    public String getFullTime(){
        String res;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long  l = Long.valueOf(getTime());
        res = sdf.format(new Date(l));//单位秒
        return res;
    }
}
