package com.atguigu.im1020.model;

import android.content.Context;

import com.atguigu.im1020.model.dao.ContactDAO;
import com.atguigu.im1020.model.dao.InvitationDAO;
import com.atguigu.im1020.model.db.DBHelpter;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DBManager {

    private final DBHelpter dbHelpter;
    private final InvitationDAO invitationDAO;
    private final ContactDAO contactDAO;

    public DBManager(Context context, String name) {
        dbHelpter = new DBHelpter(context, name);
        contactDAO = new ContactDAO(dbHelpter);
        invitationDAO = new InvitationDAO(dbHelpter);
    }

    public InvitationDAO getInvitationDAO() {
        return invitationDAO;
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }

    public void close() {
        dbHelpter.close();
    }
}
