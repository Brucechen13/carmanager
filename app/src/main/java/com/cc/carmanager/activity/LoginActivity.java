package com.cc.carmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.util.RegexUtils;
import com.cc.carmanager.util.Utils;
import com.cc.carmanager.widget.CustomEditText;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by chenc on 2017/11/3.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static String AppId = "1106433615";
    private static final int REQUEST_CODE_TO_REGISTER = 0x001;

    // 界面控件
    private CustomEditText accountEdit;
    private CustomEditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEdit = (CustomEditText) this.findViewById(R.id.et_email_phone);
        passwordEdit = (CustomEditText) this.findViewById(R.id.et_password);

        this.findViewById(R.id.btn_login).setOnClickListener(this);
        this.findViewById(R.id.iv_qq).setOnClickListener(this);
        this.findViewById(R.id.iv_wechat).setOnClickListener(this);
        this.findViewById(R.id.iv_sina).setOnClickListener(this);
        this.findViewById(R.id.tv_create_account).setOnClickListener(this);
        this.findViewById(R.id.tv_forget_password).setOnClickListener(this);
    }

    private void clickLogin() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (checkInput(account, password)) {
            // TODO: 请求服务器登录账号
        }
    }

    /**
     * 检查输入
     *
     * @param account
     * @param password
     * @return
     */
    public boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {
            Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                    .show();
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if (!RegexUtils.checkMobile(account)) {
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
            } else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 跳转到忘记密码
     */
    private void enterForgetPwd() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到注册页面
     */
    private void enterRegister() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TO_REGISTER);
    }

    public void handleData(int requestCode, int resultCode, Intent data) {
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            //notifyDataSetChanged();
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 点击使用QQ快速登录
     */
    private void clickLoginQQ() {
        if (!Utils.isQQClientAvailable(this)) {
            Toast.makeText(this, R.string.no_install_qq,
                    Toast.LENGTH_SHORT).show();
        } else {
            UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.QQ, authListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                clickLogin();
                break;
            case R.id.iv_wechat:
                UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_qq:
                clickLoginQQ();
                break;
            case R.id.iv_sina:
                UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.tv_create_account:
                enterRegister();
                break;
            case R.id.tv_forget_password:
                enterForgetPwd();
                break;
            default:
                break;
        }
    }
}
