package com.atguigu.im1020.modle.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im1020.modle.bean.UserInfo;
import com.atguigu.im1020.modle.db.AccountDB;
import com.atguigu.im1020.modle.table.AccountTable;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class AccountDAO {
    private final AccountDB DBhelper;

    public AccountDAO(Context context) {
        this.DBhelper = new AccountDB(context);
    }

    // 添加用户到数据库
    public void addAccount(UserInfo user) {
        if (user != null) {
            //得到数据库连接
            SQLiteDatabase database = DBhelper.getReadableDatabase();
            //添加对应的数据
            ContentValues contentValues = new ContentValues();
            contentValues.put(AccountTable.COL_USER_HXID, user.getHxid());
            contentValues.put(AccountTable.COL_USER_NAME, user.getUsername());
            contentValues.put(AccountTable.COL_USER_NICK, user.getNick());
            contentValues.put(AccountTable.COL_USER_PHOTO, user.getPhoto());
            //添加到数据库中
            database.replace(AccountTable.TABLE_NAME, null, contentValues);


        }
    }

    // 根据环信id获取所有用户信息
    public UserInfo getAccountByHxId(String hxId) {
        if (TextUtils.isEmpty(hxId)) {
            return null;
        }
        SQLiteDatabase database = DBhelper.getReadableDatabase();
        String sql = "select * from " + AccountTable.TABLE_NAME + " where " + AccountTable.COL_USER_HXID
                + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo = null;
        if (cursor != null && cursor.moveToNext()) {
            userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_PHOTO)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USER_NAME)));
        }
        cursor.close();
        return userInfo;
    }

}
