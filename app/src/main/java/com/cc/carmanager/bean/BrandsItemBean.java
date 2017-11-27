package com.cc.carmanager.bean;

/**
 * Created by chenc on 2017/10/26.
 */
public class BrandsItemBean implements Comparable<BrandsItemBean> {
    private String name;
    private int id;
    private String imgurl;
    private String url;
    private String pinyin;
    private char firstChar;

    public void setFirstChar(char firstChar) {
        this.firstChar = firstChar;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
        String first = pinyin.substring(0, 1);
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
            firstChar = '#';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getFirstChar() {
        return firstChar;
    }

    @Override
    public int compareTo(BrandsItemBean another) {
        return this.pinyin.compareTo(another.getPinyin());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BrandsItemBean) {
            return this.id == ((BrandsItemBean) o).getId();
        } else {
            return super.equals(o);
        }
    }
}