package com.atguigu.im1020.model;

import android.content.Context;

import com.atguigu.im1020.model.dao.AccountDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: Modle管理类
 */

public class Model {
    //恶汉式单例
    private static Model model = new Model();
    private static ExecutorService service = Executors.newCachedThreadPool();

    private Context mContext;
    private AccountDAO accountDao;

    /**
     *
     * @param run 使用线程池开启线程
     */
    public static void startThread(Runnable run){
        service.execute(run);
    }

    public void init(Context context) {
        this.mContext = context;
        this.accountDao = new AccountDAO(context);
    }

    private Model() {
    }

    public static Model getInstance() {
        return model;
    }

    public void loginSuccess(String currentUser) {
        
    }

    public AccountDAO getAccountDao() {
        return accountDao;
    }
}
