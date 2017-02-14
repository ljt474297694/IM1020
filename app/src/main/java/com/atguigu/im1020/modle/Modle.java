package com.atguigu.im1020.modle;

import android.content.Context;

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

    public ExecutorService getGlobalThread(){
        return service;
    }


    public void init(Context context) {
        this.mContext = context;
    }

    private Modle() {
    }

    public static Modle getInstance() {
        return model;
    }

}
