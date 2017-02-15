package com.atguigu.im1020.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.model.db.DBHelpter;
import com.atguigu.im1020.model.table.ContactTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: 联系人信息数据库操作类
 */

public class ContactDAO {
    private final DBHelpter dbHelpter;

    public ContactDAO(DBHelpter dbHelpter) {
        this.dbHelpter = dbHelpter;
    }

    // 获取所有联系人
    public List<UserInfo> getContacts() {
        SQLiteDatabase database = dbHelpter.getReadableDatabase();
        //如果是联系人 获取所有数据然后返回
        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = database.rawQuery(sql, null);
        List<UserInfo> userInfos = new ArrayList<>();

        UserInfo userInfo ;
        while (cursor.moveToNext()) {
            userInfo = new UserInfo();
            userInfos.add(userInfo);
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
        }
        cursor.close();
        return userInfos;
    }

    // 通过环信id获取联系人单个信息
    public UserInfo getContactByHx(String hxId) {
        if (TextUtils.isEmpty(hxId)) {
            return null;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_USER_HXID + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo = null;

        if (cursor.moveToNext()) {
            userInfo = new UserInfo();
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
        }

        cursor.close();
        return userInfo;
    }

    // 通过环信id获取用户联系人信息
    public List<UserInfo> getContactsByHx(List<String> hxIds) {

        List<UserInfo> userInfos = null;
        if (hxIds == null || hxIds.size() == 0) {
            return userInfos;
        }

        userInfos = new ArrayList<>();

        for (int i = 0; i < hxIds.size(); i++) {
            UserInfo userInfo = getContactByHx(hxIds.get(i));
            if (userInfo != null) {
                userInfos.add(userInfo);
            }
        }

        return userInfos;
    }

    // 保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact) {
        if (user == null) {
            return;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();
        ContentValues contextValues = new ContentValues();

        contextValues.put(ContactTable.COL_USER_HXID, user.getHxid());
        contextValues.put(ContactTable.COL_USER_NICK, user.getNick());
        contextValues.put(ContactTable.COL_USER_NAME, user.getUsername());
        contextValues.put(ContactTable.COL_USER_PHOTO, user.getPhoto());
        contextValues.put(ContactTable.COL_IS_CONTACT, isMyContact ? 1 : 0);


        database.replace(ContactTable.TABLE_NAME, null, contextValues);
    }


    // 保存联系人信息
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact) {

        if(contacts==null||contacts.size()==0) {
            return ;
        }

        for(int i = 0; i <contacts.size() ; i++) {
            saveContact(contacts.get(i),isMyContact);
        }
    }

    // 删除联系人信息
    public void deleteContactByHxId(String hxId) {
        if(TextUtils.isEmpty(hxId)) {
            return;
        }
        SQLiteDatabase database = dbHelpter.getReadableDatabase();

        database.delete(ContactTable.TABLE_NAME,ContactTable.COL_USER_HXID+"=?",new String []{hxId});
    }

}
