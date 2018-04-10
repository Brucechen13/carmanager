package com.cc.carmanager.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.AnswerQuestionActivity;
import com.cc.carmanager.activity.ComsumerAskActivity;
import com.cc.carmanager.activity.IndexArticleActivity;
import com.cc.carmanager.activity.LoginActivity;
import com.cc.carmanager.activity.NewsCommentsActivity;
import com.cc.carmanager.bean.PostStatusBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.LoginHelper;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.widget.CircleDrawable;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

public class ProfileFragment extends LazyFragment implements View.OnClickListener {
    private ImageView userImg;
    private TextView nameTv;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment_layout);
        initView();

        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_USER_ISLOGIN, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                PostStatusBean mRecommendBean = gson.fromJson(resultStr, PostStatusBean.class);
                if(mRecommendBean.isSuccess()){
                    LoginHelper.instance.setLogin();
                }
                initDatas();
            }

            @Override
            public void failure() {
                Log.d("car", "新闻内容网络数据解析失败");
            }
        });
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        initDatas();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    protected void initView() {
        userImg = (ImageView)findViewById(R.id.fra_mine_user_img);
        nameTv = (TextView)findViewById(R.id.fra_mine_user_name_tv);
        findViewById(R.id.settle_collect).setOnClickListener(this);
        findViewById(R.id.settle_like).setOnClickListener(this);
        findViewById(R.id.settle_comment).setOnClickListener(this);
        findViewById(R.id.settle_question).setOnClickListener(this);
        findViewById(R.id.settle_ask).setOnClickListener(this);
    }

    protected void initDatas() {
        userImg.setOnClickListener(this);

        if(LoginHelper.instance.checkLogin()){
            nameTv.setText(LoginHelper.instance.getUserName());
            //设置登录图片
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.user_login);
            CircleDrawable circleBitmap = new CircleDrawable(bitmap);
            userImg.setImageDrawable(circleBitmap);

        }else {
            nameTv.setText("请登录");
            //设置圆形图片
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.user_default_common);
            CircleDrawable circleBitmap = new CircleDrawable(bitmap);
            userImg.setImageDrawable(circleBitmap);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fra_mine_user_img:

                Intent intent=new Intent(this.getActivity(), LoginActivity.class);
                startActivityForResult(intent, 102);
                break;
            case R.id.settle_comment:
                LoginHelper.instance.isLogin(ProfileFragment.this.getActivity(), new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Intent intent = new Intent(getActivity(), NewsCommentsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "我的评论");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
            case R.id.settle_like:
                LoginHelper.instance.isLogin(ProfileFragment.this.getActivity(), new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Intent intent = new Intent(getActivity(), IndexArticleActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "我的点赞");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
            case R.id.settle_collect:
                LoginHelper.instance.isLogin(ProfileFragment.this.getActivity(), new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Intent intent = new Intent(getActivity(), IndexArticleActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "我的收藏");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
            case R.id.settle_question:
                LoginHelper.instance.isLogin(ProfileFragment.this.getActivity(), new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Intent intent = new Intent(getActivity(), AnswerQuestionActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
            case R.id.settle_ask:
                LoginHelper.instance.isLogin(ProfileFragment.this.getActivity(), new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        Intent intent = new Intent(getActivity(), ComsumerAskActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 103) {
            initDatas();
        }
    }

}
