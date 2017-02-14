package com.atguigu.im1020.modle;

import android.content.Context;

import com.atguigu.im1020.modle.dao.AccountDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class Modle {
    private static Modle model = new Modle();
    private ExecutorService service = Executors.newCachedThreadPool();

    private Context mContext;
    private AccountDAO accountDao;

    /**
     *
     * @param run 使用线程池开启线程
     */
    public void startThread(Runnable run){
        service.execute(run);
    }
    public void init(Context context) {
        this.mContext = context;
        this.accountDao = new AccountDAO(context);
    }

    private Modle() {
    }

    public static Modle getInstance() {
        return model;
    }

    public void loginSuccess(String currentUser) {
        
    }

    public AccountDAO getAccountDao() {
        return accountDao;
    }
}
