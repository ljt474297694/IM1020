package com.atguigu.im1020.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im1020.model.bean.GroupInfo;
import com.atguigu.im1020.model.bean.InvitationInfo;
import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.model.db.DBHelpter;
import com.atguigu.im1020.model.table.InvitationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class InvitationDAO {

    private final DBHelpter dbHelpter;

    public InvitationDAO(DBHelpter dbHelpter) {
        this.dbHelpter = dbHelpter;
    }

    // 添加邀请
    public void addInvitation(InvitationInfo invitationInfo) {
        if (invitationInfo == null) {
            return;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();
        ContentValues contentvalus = new ContentValues();

        //先添加公共部分
        contentvalus.put(InvitationTable.COL_REASON, invitationInfo.getReason());
        contentvalus.put(InvitationTable.COL_STATUS, invitationInfo.getStatus().ordinal());

        UserInfo userInfo = invitationInfo.getUserInfo();
        if (userInfo != null) {
            //如果不为空 此时是联系人邀请
            contentvalus.put(InvitationTable.COL_USER_HXID, invitationInfo.getUserInfo().getHxid());
            contentvalus.put(InvitationTable.COL_USER_NAME, invitationInfo.getUserInfo().getUsername());
        } else {
            //如果为空 那么是群组邀请
            contentvalus.put(InvitationTable.COL_GROUP_ID, invitationInfo.getGroupInfo().getGroupid());
            contentvalus.put(InvitationTable.COL_GROUP_NAME, invitationInfo.getGroupInfo().getGroupName());
            contentvalus.put(InvitationTable.COL_USER_HXID, invitationInfo.getGroupInfo().getInvitePerson());

        }
        database.replace(InvitationTable.TABLE_NAME, null, contentvalus);

    }

    // 获取所有邀请信息
    public List<InvitationInfo> getInvitations() {

        SQLiteDatabase database = dbHelpter.getReadableDatabase();
        String sql = "select * from " + InvitationTable.TABLE_NAME;

        Cursor cursor = database.rawQuery(sql, null);
        List<InvitationInfo> infoList = null;
        InvitationInfo invitationInfo = null;

        while (cursor.moveToNext()) {
            //进入此循环表示有数据 第一次创建集合 否则没有数据返回null
            if (infoList == null) {
                infoList = new ArrayList<>();
            }

            invitationInfo = new InvitationInfo();

            infoList.add(invitationInfo);

            invitationInfo.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
            invitationInfo.setStatus(int2InviteStatus(
                    cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATUS))));


            String groupid = cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_ID));
            if (groupid != null) {
                //群组
                GroupInfo groupInfo = new GroupInfo();
                invitationInfo.setGroupInfo(groupInfo);

                groupInfo.setGroupid(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_ID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_NAME)));
                groupInfo.setInvitePerson(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));


            } else {
                //联系人
                UserInfo userInfo = new UserInfo();
                invitationInfo.setUserInfo(userInfo);

                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));
                userInfo.setUsername(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_NAME)));
            }

        }
        cursor.close();
        return infoList;
    }

    // 将int类型状态转换为邀请的状态
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }


    // 删除邀请
    public void removeInvitation(String hxId) {
        if (TextUtils.isEmpty(hxId)) {
            return;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        database.delete(InvitationTable.TABLE_NAME,InvitationTable.COL_USER_HXID+"=?",new String[]{hxId});


    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus, String hxId){

        if(TextUtils.isEmpty(hxId)||invitationStatus==null) {
            return ;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationTable.COL_STATUS,invitationStatus.ordinal());
        database.update(InvitationTable.TABLE_NAME,contentValues,
                InvitationTable.COL_USER_HXID+"=?",new String[]{hxId});
    }
}
