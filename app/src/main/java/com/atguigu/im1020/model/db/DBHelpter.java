package com.atguigu.im1020.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.im1020.model.table.ContactTable;
import com.atguigu.im1020.model.table.InvitationTable;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DBHelpter extends SQLiteOpenHelper {

    public DBHelpter(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_TABLE);
        db.execSQL(InvitationTable.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
