package com.cc.carmanager.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.LoginActivity;
import com.cc.carmanager.widget.CircleDrawable;
import com.shizhefei.fragment.LazyFragment;

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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.user_default_common);
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
                Intent intent=new Intent(this.getActivity(), LoginActivity.class);
                startActivityForResult(intent,102);
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
