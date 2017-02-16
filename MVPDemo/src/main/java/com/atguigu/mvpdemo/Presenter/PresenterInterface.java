package com.atguigu.mvpdemo.presenter;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: 操作层接口
 */

public interface PresenterInterface {
    //请求成功回调接口
    void onSuccess(String json);

    //当请求失败 回调接口
    void onError(String error);

    //view层初始化完毕 调用此方法开始访问网络
    void getDataFromNet();
}
