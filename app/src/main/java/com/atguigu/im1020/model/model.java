package com.atguigu.im1020.model;

import android.content.Context;
import android.text.TextUtils;

import com.atguigu.im1020.model.dao.AccountDAO;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: Modle管理类
 */

public class Model {

    private Context mContext;
    private AccountDAO accountDao;
    private DBManager dbManager;

    /**
     * 静态内部类 单例模式
     */
    private static class ModelTool{
        private static final Model MODEL = new Model();
    }
    public static Model getInstance() {
        return ModelTool.MODEL;
    }


    public void init(Context context) {
        this.mContext = context;
        this.accountDao = new AccountDAO(context);
        new GlobalListener(context);
    }

    private Model() {
    }

    public void loginSuccess(String currentUser) {
        if(TextUtils.isEmpty(currentUser)) {
            return;
        }
        if(dbManager!=null) {
            dbManager.close();
        }
        dbManager = new DBManager(mContext,currentUser+".db");
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public AccountDAO getAccountDao() {
        return accountDao;
    }
}
