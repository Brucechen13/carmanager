package com.cc.carmanager.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;

/**
 * Created by chenc on 2017/12/12.
 */

public abstract class BarBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleTv;
    private ImageView backIv;

    private void initView(){
        if(titleTv == null){
            titleTv = (TextView)findViewById(R.id.action_bar_title_tv);
            backIv = (ImageView)findViewById(R.id.action_bar_back_iv);
            backIv.setOnClickListener(this);
        }
    }

    protected void setHeader(String title){
        initView();
        titleTv.setText(title);
        backIv.setImageResource(R.mipmap.icon_return);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_bar_back_iv:
                this.finish();
                break;
        }
    }
}
