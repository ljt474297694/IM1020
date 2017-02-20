package com.atguigu.im1020.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.atguigu.im1020.R;
import com.atguigu.im1020.utils.Constant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

public class ChatActivity extends AppCompatActivity {

    private EaseChatFragment chatFragment;
    private String groupid;
    private BroadcastReceiver recriver;
    private LocalBroadcastManager manager;

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


        int type = getIntent().getExtras().getInt(EaseConstant.EXTRA_CHAT_TYPE);

        groupid = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);

        if(type == EaseConstant.CHATTYPE_GROUP) {
            manager = LocalBroadcastManager.getInstance(getApplicationContext());
            recriver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                        if(groupid.equals(intent.getStringExtra("groupid"))) {
                            finish();
                        }
                }
            };
            manager.registerReceiver(recriver,new IntentFilter(Constant.DESTORY_GROUP));
        }
    }

    @Override
    protected void onDestroy() {
        if(recriver!=null) {
            manager.unregisterReceiver(recriver);
        }
        super.onDestroy();
    }
}
