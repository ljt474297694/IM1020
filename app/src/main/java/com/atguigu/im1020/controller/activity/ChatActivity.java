package com.atguigu.im1020.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.atguigu.im1020.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
    }

    private void initData() {
        EaseChatFragment easeChatFragment = new EaseChatFragment();

        easeChatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_chat,easeChatFragment).commit();
    }
}
