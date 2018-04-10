package com.cc.carmanager.bean;

import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class NavListBean {
    private List<NavItemBean> data;
    private boolean success;

    public List<NavItemBean> getData() {
        return data;
    }

    public void setData(List<NavItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class NavItemBean{
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
