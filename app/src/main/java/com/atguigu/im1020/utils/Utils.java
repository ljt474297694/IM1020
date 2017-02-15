package com.atguigu.im1020.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: 工具类
 */

public class Utils {
    private static ExecutorService service = Executors.newCachedThreadPool();
    /**
     *
     * @param run 使用线程池启动线程事物
     */
    public static void startThread(Runnable run){
        service.execute(run);
    }
}
