package com.cc.carmanager.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.BoringLayout;
import android.util.Log;

import com.cc.carmanager.MyApplication;
import com.cc.carmanager.activity.LoginActivity;
import com.cc.carmanager.bean.PostStatusBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.google.gson.Gson;

import javax.security.auth.callback.Callback;

/**
 * Created by chenc on 2018/2/11.
 */

public class LoginHelper {
    public static LoginHelper instance = new LoginHelper();
    private boolean islogin = false;
    private int userId;
    SharedPreferences preferences = null;
    private String userName = "";


    public void isLogin(final Context context, final VolleyResult result){
        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_USER_ISLOGIN, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                PostStatusBean mRecommendBean = gson.fromJson(resultStr, PostStatusBean.class);
                if(mRecommendBean.isSuccess()){
                    if(!islogin){
                        setLogin();
                    }
                    result.success(resultStr);
                }else{
                    Intent intent=new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void failure() {
                Log.d("car", "新闻内容网络数据解析失败");
            }
        });
    }

    public boolean checkLogin() {
        return islogin;
    }

    public String getUserName(){
        return userName;
    }

    public void setLogin(){
        if(preferences == null){
            preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MyApplication.getContext().MODE_PRIVATE);
        }
        userName = preferences.getString("username", "未登录");
        islogin = true;
    }

    public void setLogin(String username){
        if(preferences == null){
            preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MyApplication.getContext().MODE_PRIVATE);
        }
        islogin = true;
        userName = username;
        //获取Editor对象
        SharedPreferences.Editor editor=preferences.edit();
        //设置参数
        editor.putString("username", username);
        //提交
        editor.apply();
    }

    public void saveSettingNote(String saveData){//保存设置
        if(preferences == null){
            preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MyApplication.getContext().MODE_PRIVATE);
        }
        //获取Editor对象
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("cookie", saveData);
        editor.apply();
    }

    public String getSettingNote(){//获取保存设置
        if(preferences == null){
            preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MyApplication.getContext().MODE_PRIVATE);
        }
        return preferences.getString("cookie", "");
    }

}
