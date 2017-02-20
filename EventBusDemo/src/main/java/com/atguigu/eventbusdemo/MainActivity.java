package com.atguigu.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView tv_main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_main = (TextView) findViewById(R.id.tv_main);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销事件接受
        EventBus.getDefault().unregister(this);
    }

    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity1.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusMassage message) {
        tv_main.setText(message.getMessage());
    }


}
