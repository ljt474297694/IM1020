package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.atguigu.im1020.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

public class ChatActivity extends AppCompatActivity {

    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
        
        initListener();
        
    }

    private void initListener() {
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

                startActivity(new Intent(ChatActivity.this,ChatDetailsActivity.class)
                .putExtra("groupid",getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID)));

            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
    }

    private void initData() {
        chatFragment = new EaseChatFragment();

        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_chat, chatFragment).commit();
    }
}
