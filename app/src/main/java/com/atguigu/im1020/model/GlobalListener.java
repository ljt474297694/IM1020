package com.atguigu.im1020.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.im1020.model.bean.InvitationInfo;
import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.utils.Constant;
import com.atguigu.im1020.utils.SpUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GlobalListener {

    private final LocalBroadcastManager lbm;

    public GlobalListener(Context context) {

        EMClient.getInstance().contactManager().setContactListener(listener);
        lbm = LocalBroadcastManager.getInstance(context);
    }

    EMContactListener listener = new EMContactListener() {

        //收到好友请  别人加你
        @Override
        public void onContactInvited(String username, String reason) {

            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setUserInfo(new UserInfo(username));
            info.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGED));

        }

        //好友请求被同意  你加别人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {
            InvitationInfo info = new InvitationInfo();
            info.setReason("加为好友");
            info.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
            info.setUserInfo(new UserInfo(username));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);

            lbm.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGED));
        }

        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {

            Model.getInstance().getDbManager().getContactDAO().deleteContactByHxId(username);
            Model.getInstance().getDbManager().getInvitationDAO().removeInvitation(username);


            lbm.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));

        }


        //增加了联系人时回调此方法  当你同意添加好友
        @Override
        public void onContactAdded(String username) {
            Model.getInstance().getDbManager().getContactDAO().saveContact(new UserInfo(username), true);


            lbm.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGED));

        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);

            lbm.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGED));

        }
    };
}
