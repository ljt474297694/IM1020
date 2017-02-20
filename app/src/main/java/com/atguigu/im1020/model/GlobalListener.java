package com.atguigu.im1020.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.im1020.model.bean.GroupInfo;
import com.atguigu.im1020.model.bean.InvitationInfo;
import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.utils.Constant;
import com.atguigu.im1020.utils.SpUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
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
        EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
        lbm = LocalBroadcastManager.getInstance(context);
    }

    EMGroupChangeListener groupListener = new EMGroupChangeListener() {

        //收到加入群组的邀请  别的组邀请自己
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {


            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setStatus(InvitationInfo.InvitationStatus.NEW_GROUP_INVITE);
            info.setGroupInfo(new GroupInfo(groupName,groupId,inviter));

            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);


            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        //群组邀请被拒绝  你邀请别人被拒绝
        @Override
        public void onInvitationDeclined(String groupId, String invitee, String reason) {



            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setStatus(InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED);
            info.setGroupInfo(new GroupInfo(groupId,groupId,invitee));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        //群组邀请被接受  你邀请别人 别人接受了
        @Override
        public void onInvitationAccepted(String groupId, String inviter,
                                         String reason) {


            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setStatus(InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);
            info.setGroupInfo(new GroupInfo(groupId,groupId,inviter));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);


            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        //收到加群申请   别人要加你的群
        @Override
        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {


            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setStatus(InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION);
            info.setGroupInfo(new GroupInfo(groupName,groupId,applyer));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);


            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        //加群申请被同意 你加别人的群 别人同意了
        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {
            InvitationInfo info = new InvitationInfo();
            info.setReason("");
            info.setStatus(InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED);
            info.setGroupInfo(new GroupInfo(groupName,groupId,accepter));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);


            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        // 加群申请被拒绝  你加别人的群 别人拒绝了
        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setStatus(InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED);
            info.setGroupInfo(new GroupInfo(groupName,groupId,decliner));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }

        //当前用户被管理员移除出群组
        @Override
        public void onUserRemoved(String groupId, String groupName) {

        }

        //群组被创建者解散
        @Override
        public void onGroupDestroyed(String groupId, String groupName) {

        }

        //收到 群邀请被自动接受
        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId,
                                                    String inviter, String inviteMessage) {

            InvitationInfo info = new InvitationInfo();
            info.setReason(inviteMessage);
            info.setStatus(InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);
            info.setGroupInfo(new GroupInfo(groupId,groupId,inviter));
            Model.getInstance().getDbManager().getInvitationDAO().addInvitation(info);

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);
            lbm.sendBroadcast(new Intent(Constant.NEW_GROUP_CHANGED));
        }
    };
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
            info.setReason("邀请成功");
            info.setUserInfo(new UserInfo(username));
            info.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
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


            lbm.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));

        }

        //好友请求被拒绝  你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

            SpUtils.getInstace().save(SpUtils.NEW_INVITE, true);

            lbm.sendBroadcast(new Intent(Constant.NEW_INVITE_CHANGED));

        }
    };
}
