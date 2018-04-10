package com.cc.carmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.cc.carmanager.R;
import com.cc.carmanager.bean.PostStatusBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.LoginHelper;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.RegexUtils;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.util.VerifyCodeManager;
import com.cc.carmanager.widget.CustomEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenc on 2017/10/31.
 */
public class SignUpActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";
    // 界面控件
    private CustomEditText phoneEdit;
    private CustomEditText passwordEdit;
    private CustomEditText verifyCodeEdit;
    private CustomEditText userNameEdit;
    private Button getVerifiCodeButton;

    private VerifyCodeManager codeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        codeManager = new VerifyCodeManager(this, phoneEdit, getVerifiCodeButton);

    }

    /**
     * 通用findViewById,减少重复的类型转换
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }


    private void initViews() {

        phoneEdit = getView(R.id.et_phone);
        phoneEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        verifyCodeEdit = getView(R.id.et_verifiCode);
        verifyCodeEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        passwordEdit = getView(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        userNameEdit = getView(R.id.et_nickname);
        userNameEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        userNameEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        userNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // 点击虚拟键盘的done
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    commit();
                }
                return false;
            }
        });
        findViewById(R.id.btn_create_account).setOnClickListener(this);
    }

    private void commit() {
        String phone = phoneEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String code = verifyCodeEdit.getText().toString().trim();
        final String userName = userNameEdit.getText().toString().trim();

        if (checkInput(phone, password, code, userName)) {
            // TODO:请求服务端注册账号
            Map<String, String> params = new HashMap<>();
            params.put("phone", phone);
            params.put("pwd", password);
            params.put("email", code);
            params.put("userName", userName);

            VolleyInstance.getVolleyInstance().startJsonObjectPost(NetUrlsSet.URL_USER_SIGN, params, new VolleyResult() {
                @Override
                public void success(String resultStr) {
                    Log.e("car", resultStr);
                    Gson gson=new Gson();
                    PostStatusBean mRecommendBean=gson.fromJson(resultStr,PostStatusBean.class);
                    if(mRecommendBean.isSuccess()){
                        ToastUtils.makeLongText("注册成功", SignUpActivity.this);
                        finish();
                    }else{
                        ToastUtils.makeShortText("注册失败", SignUpActivity.this);
                    }
                }
                @Override
                public void failure() {
                    Log.d("car", "新闻内容网络数据解析失败");
                }
            });
        }
    }

    private boolean checkInput(String phone, String password, String code, String userName) {
        if (TextUtils.isEmpty(phone)) { // 电话号码为空
            ToastUtils.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtils.checkMobile(phone)) { // 电话号码格式有误
                ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) { // 邮箱不正确
                ToastUtils.showShort(this, R.string.tip_please_input_code);
            }else if(!RegexUtils.checkEmail(code)) {
                ToastUtils.showShort(this, R.string.tip_email_regex_not_right);
            }else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { // 密码格式
                ToastUtils.showShort(this,
                        R.string.tip_please_input_6_32_password);
            } else if(TextUtils.isEmpty(userName)) {
                ToastUtils.showShort(this, R.string.tip_please_input_name);
            }else{
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码
                codeManager.getVerifyCode(VerifyCodeManager.REGISTER);
                break;
            case R.id.btn_create_account:
                commit();
            default:
                break;
        }
    }
}
