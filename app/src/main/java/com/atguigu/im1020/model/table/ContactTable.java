package com.atguigu.im1020.model.table;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ContactTable {
    public static final String TABLE_NAME = "contact";

    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_HXID = "hxid";
    public static final String COL_USER_NICK = "nick";
    public static final String COL_USER_PHOTO = "photo";
    public static final String COL_IS_CONTACT = "is_contact";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_USER_HXID + " text primary key,"
            + COL_USER_NAME + " text,"
            + COL_USER_PHOTO + " text,"
            + COL_USER_NICK + " text,"
            + COL_IS_CONTACT + " Integer);";
}
