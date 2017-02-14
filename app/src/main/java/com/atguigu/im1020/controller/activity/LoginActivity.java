package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.atguigu.im1020.R;
import com.atguigu.im1020.modle.Modle;
import com.atguigu.im1020.modle.bean.UserInfo;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_et_username)
    EditText loginEtUsername;
    @Bind(R.id.login_et_password)
    EditText loginEtPassword;
    @Bind(R.id.login_btn_register)
    Button loginBtnRegister;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;

    private String pwd;
    private String username;

    private Modle modle = Modle.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_btn_register, R.id.login_btn_login})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_btn_register:
                if (verify()) {
                    modle.getGlobalThread().execute(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                EMClient.getInstance().createAccount(username, pwd);
                                ShowToast.showUIThread(LoginActivity.this, "注册成功");

                            } catch (HyphenateException e) {
                                e.printStackTrace();

                                ShowToast.showUIThread(LoginActivity.this, "注册失败" + e.getMessage());
                            }
                        }

                    });
                }
                break;

            case R.id.login_btn_login:
                if (verify()) {
                    modle.getGlobalThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            EMClient.getInstance().login(username, pwd, new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    //登录成功后需要的处理
                                    modle.loginSuccess(EMClient.getInstance().getCurrentUser());

                                    //将用户保存到数据库
                                    modle.getAccountDao()
                                            .addAccount(new UserInfo(EMClient.getInstance().getCurrentUser()));
                                    Log.e("TAG", "LoginActivity onSuccess()+数据库保存成功");
                                    //登录成功切换主页面 结束当前页面
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                    finish();
                                }
                                @Override
                                public void onError(int i, String s) {
                                    Log.e("TAG", "LoginActivity onError()" + s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        }

                    });
                }
                break;
        }
    }

    private boolean verify() {
        pwd = loginEtPassword.getText().toString().trim();
        username = loginEtUsername.getText().toString().trim();
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(username)) {
            ShowToast.showUIThread(this, "账户或密码不能为空");
            return false;
        }
        return true;
    }
}
