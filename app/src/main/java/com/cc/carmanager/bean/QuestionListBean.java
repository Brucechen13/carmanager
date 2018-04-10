package com.cc.carmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class QuestionListBean {

    private List<QuestionItemBean> data;
    private boolean success;

    public List<QuestionItemBean> getData() {
        return data;
    }

    public void setData(List<QuestionItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class QuestionItemBean implements Serializable {
        private String questionContent;
        private String a;
        private String b;
        private String c;
        private String d;
        private String right;
        private String select;
        private int id;
        private int type;

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }

        public String getQuestionContent() {
            return questionContent;
        }

        public void setQuestionContent(String questionContent) {
            this.questionContent = questionContent;
        }

        public String getA() {
            if(a == null)return "";
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            if(b == null)return "";
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            if(c == null)return "";
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            if(d == null)return "";
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
