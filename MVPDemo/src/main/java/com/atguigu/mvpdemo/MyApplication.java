package com.atguigu.mvpdemo;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: MyApplication 初始化
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
    }
    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(5000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
