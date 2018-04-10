package com.cc.carmanager.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.bean.NewsCommentsBean;
import com.cc.carmanager.bean.NewsContentBean;
import com.cc.carmanager.bean.NewsFullBean;
import com.cc.carmanager.bean.PostStatusBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.LoginHelper;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.util.URLImageParser;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by chenc on 2017/10/24.
 */
public class NewsDisplayActivity extends BarBaseActivity {
    //private SystemBarTintManager tintManager;
    private Context context;
    private TextView content;
    private TextView title;
    private TextView authorAndTime;
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private String link;
    private final String template = "<p><img src='LINK'/></p>";
    private int news_id;

    private WebView webView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_news_display);

        setHeader("新能源汽车");

        content = (TextView)findViewById(R.id.tv_content);
        title = (TextView)findViewById(R.id.tv_newstitle);
        authorAndTime = (TextView)findViewById(R.id.tv_author_time);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        webView = (WebView)findViewById(R.id.webview);

        // 启用javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.setWebViewClient(new WebViewClient());

        findViewById(R.id.comment).setOnClickListener(this);
        findViewById(R.id.collect).setOnClickListener(this);
        findViewById(R.id.like).setOnClickListener(this);
        final EditText editText = (EditText)findViewById(R.id.text_input);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEND){
                    if(!LoginHelper.instance.checkLogin()){
                        Intent intent=new Intent(NewsDisplayActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    String text = editText.getText().toString();
                    sendComment(text);
                    editText.clearFocus();
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });

        news_id = getIntent().getExtras().getInt("news_id", 90);
        getNews(news_id);

    }

    private void sendComment(String comment){
        Map<String, String> params = new HashMap<>();
        params.put("newsId", ""+news_id);
        params.put("content", comment);
        VolleyInstance.getVolleyInstance().startJsonObjectPost(NetUrlsSet.URL_CAR_COMMENT, params, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Log.e("car", resultStr);
                Gson gson=new Gson();
                PostStatusBean mRecommendBean=gson.fromJson(resultStr,PostStatusBean.class);
                if(mRecommendBean.isSuccess()){
                    ToastUtils.makeLongText("评论完成", NewsDisplayActivity.this);
                }else{
                    ToastUtils.makeShortText("评论上传失败", NewsDisplayActivity.this);
                }
            }
            @Override
            public void failure() {
                Log.d("car", "新闻内容网络数据解析失败");
            }
        });
    }

    private void getNews(int news_id) {
        VolleyInstance.getVolleyInstance().startRequest(String.format(NetUrlsSet.URL_NEWS_CONTENT, news_id), new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson=new Gson();
                NewsFullBean mRecommendBean=gson.fromJson(resultStr,NewsFullBean.class);
                if(mRecommendBean.isSuccess()){
                    updateViewFromJSON(mRecommendBean.getData());
                }else{
                    ToastUtils.makeShortText("新闻加载失败", NewsDisplayActivity.this);
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "新闻内容网络数据解析失败");
            }
        });
    }


    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onClick(View view){
        super.onClick(view);
        switch (view.getId()){
            case R.id.comment:
                Intent intent = new Intent(this, NewsCommentsActivity.class);
                intent.putExtra("news_id", news_id);
                startActivity(intent);
                break;
            case R.id.like:
                LoginHelper.instance.isLogin(NewsDisplayActivity.this, new VolleyResult() {
                    @Override
                    public void success(String resultStr) {VolleyInstance.getVolleyInstance().startRequest(String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_LIKE, news_id), new VolleyResult(){
                        @Override
                        public void success(String resultStr) {
                            Gson gson = new Gson();
                            PostStatusBean mRecommendBean = gson.fromJson(resultStr, PostStatusBean.class);
                            if(mRecommendBean.isSuccess()){
                                ToastUtils.makeLongText("点赞成功", NewsDisplayActivity.this);
                            }else{
                                ToastUtils.makeLongText("点赞失败", NewsDisplayActivity.this);
                            }
                        }
                        @Override
                        public void failure() {
                            Log.d("car", "新闻内容网络数据解析失败");
                        }
                    });
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
            case R.id.collect:
                LoginHelper.instance.isLogin(NewsDisplayActivity.this, new VolleyResult() {
                    @Override
                    public void success(String resultStr) {
                        VolleyInstance.getVolleyInstance().startRequest(String.format(Locale.CHINA, NetUrlsSet.URL_NEWS_COLLECT, news_id), new VolleyResult() {
                            @Override
                            public void success(String resultStr) {
                                Gson gson = new Gson();
                                PostStatusBean mRecommendBean = gson.fromJson(resultStr, PostStatusBean.class);
                                if(mRecommendBean.isSuccess()){
                                    ToastUtils.makeLongText("收藏成功", NewsDisplayActivity.this);
                                }else{
                                    ToastUtils.makeLongText("收藏失败", NewsDisplayActivity.this);
                                }
                            }
                            @Override
                            public void failure() {
                                Log.d("car", "新闻内容网络数据解析失败");
                            }
                        });
                    }

                    @Override
                    public void failure() {

                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updateViewFromJSON(NewsContentBean newRoot) {
        //设置标题
        title.setText(newRoot.getTitle());

        //设置作者和时间
        authorAndTime.setText(newRoot.getAuthor() + "    " + newRoot.getCreateTime().getFullTime());

        //设置正文
        String body = newRoot.getContentMobile();
        Log.e("car", body);
        //webView.loadDataWithBaseURL( null, body , "text/html", "UTF-8", null );
        webView.loadDataWithBaseURL("hhtp://www.baidu.com", body,"text/html; charset=utf-8", "UTF-8", null);
    }
}
