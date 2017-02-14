package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    private int time = 6000;
    @Bind(R.id.splash_tv_timer)
    TextView splashTvTimer;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (time > 0) {
                        time -= 1000;
                        splashTvTimer.setText(time / 1000 + "S");
                        sendEmptyMessageDelayed(0, 1000);
                    } else {
                        enterMainOrLogin();
                    }
                    break;
            }
        }
    };

    //进入主界面或者登录界面
    private void enterMainOrLogin() {
        new Thread() {
            public void run() {
                //去环信服务器 获取是否登录过
                boolean loggedInBefore = EMClient.getInstance().isLoggedInBefore();
                if (loggedInBefore) {

                    //登陆过
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    //退出当前页面
                    finish();
                } else {
                    //没有登录过

                    //跳转到登录页面
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    //退出当前页面
                    finish();
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //发送消息
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        //移除所有未处理的消息
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @OnClick(R.id.splash_tv_timer)
    public void onClick() {
        handler.removeCallbacksAndMessages(null);
        enterMainOrLogin();
    }
}
