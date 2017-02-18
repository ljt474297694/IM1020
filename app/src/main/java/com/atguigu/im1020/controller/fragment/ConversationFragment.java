package com.atguigu.im1020.controller.fragment;

import android.content.Intent;

import com.atguigu.im1020.controller.activity.ChatActivity;
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
 * 功能: xxxx
 */

public class ConversationFragment extends EaseConversationListFragment {


    @Override
    protected void initView() {
        super.initView();


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
}
