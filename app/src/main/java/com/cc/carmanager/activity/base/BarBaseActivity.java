package com.cc.carmanager.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;

/**
 * Created by chenc on 2017/12/12.
 */

public abstract class BarBaseActivity extends AppCompatActivity {

    private TextView titleTv;
    private ImageView backIv;

    private void initView(){
        if(titleTv == null){
            titleTv = (TextView)findViewById(R.id.action_bar_title_tv);
            backIv = (ImageView)findViewById(R.id.action_bar_back_iv);
        }
    }

    protected void setHeader(String title){
        initView();
        titleTv.setText(title);
        backIv.setImageResource(R.mipmap.back_icon);
    }
}
