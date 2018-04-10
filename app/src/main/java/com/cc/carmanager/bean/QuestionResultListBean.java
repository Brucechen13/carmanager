package com.cc.carmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenc on 2018/4/8.
 */

public class QuestionResultListBean {

    private List<QuestionResItemBean> data;
    private boolean success;

    public List<QuestionResItemBean> getData() {
        return data;
    }

    public void setData(List<QuestionResItemBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class QuestionResItemBean implements Serializable {
        private String answer;
        private String id;
        private boolean isCorrect;
        private int score;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }
    }
}
