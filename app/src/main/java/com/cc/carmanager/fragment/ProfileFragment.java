package com.cc.carmanager.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.ForgetPasswordActivity;
import com.cc.carmanager.activity.SignUpActivity;
import com.cc.carmanager.util.RegexUtils;
import com.cc.carmanager.util.Utils;
import com.cc.carmanager.widget.CircleDrawable;
import com.cc.carmanager.widget.CustomEditText;
import com.shizhefei.fragment.LazyFragment;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zhouwei on 17/4/23.
 */

public class ProfileFragment extends LazyFragment implements View.OnClickListener {
    private ImageView userImg;
    private TextView nameTv;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment_layout);
        initView();
        initDatas();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    protected void initView() {
        userImg = (ImageView)findViewById(R.id.fra_mine_user_img);
        nameTv = (TextView)findViewById(R.id.fra_mine_user_name_tv);
    }

    protected void initDatas() {
        //设置圆形图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ahlib_userpic_default);
        CircleDrawable circleBitmap = new CircleDrawable(bitmap);
        userImg.setImageDrawable(circleBitmap);

        userImg.setOnClickListener(this);

//        SharedPreferences sp= getContext().getSharedPreferences("LoginState",
//                getContext().MODE_PRIVATE);

//        if (sp.getBoolean("isLogin",false)){
//            nameTv.setText(sp.getString("name","请登录"));
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fra_mine_user_img:
//                Intent intent=new Intent(mContext, LoginActivity.class);
//                //Log.d("aaa", "触发点击事件");
//                startActivityForResult(intent,102);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 103) {
            String name = data.getStringExtra("name");
            nameTv.setText(name);
            //将登陆的状态存储到sp中
//            SharedPreferences sp=mContext.getSharedPreferences("LoginState",mContext.MODE_PRIVATE);
//            SharedPreferences.Editor editor=sp.edit();
//            editor.putBoolean("isLogin",true);
//            editor.putString("name",name);
//            editor.commit();
        }
    }

}
