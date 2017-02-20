package com.atguigu.im1020.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.im1020.controller.activity.ChatActivity;
import com.atguigu.im1020.utils.Constant;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: 会话列表的Fragment
 */

public class ConversationFragment extends EaseConversationListFragment {

    private LocalBroadcastManager manager;
    private NotifyReceiver notifyReceiver;

    @Override
    protected void initView() {
        super.initView();

        manager = LocalBroadcastManager.getInstance(getActivity());
        notifyReceiver = new NotifyReceiver();
        manager.registerReceiver(notifyReceiver,new IntentFilter(Constant.CONTACT_CHANGED));



        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), ChatActivity.class)
                        .putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());

                if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    //传入群聊天类型
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                }

                startActivity(intent);
            }
        });


        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                EaseUI.getInstance().getNotifier().onNewMesg(list);
                refresh();

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }

    /**
     * @param hidden 在Fragment 隐藏与显示切换时调用 如果是隐藏返回true 显示返回false
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
//            conversationList.clear();
        }
    }

    class NotifyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.CONTACT_CHANGED:
                    refresh();
                    break;
            }
        }
    }
}
