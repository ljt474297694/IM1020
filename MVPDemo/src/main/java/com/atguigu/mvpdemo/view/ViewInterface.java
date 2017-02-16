package com.atguigu.mvpdemo.view;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: View层接口 回调显示视图 绑定数据
 */

public interface ViewInterface {

    //请求成功 绑定数据
    void onSuccess(String json);
    //请求错误 返回错误数据
    void onError(String error);
    //显示加载视图
    void showLoading();
    //隐藏加载视图
    void hideLoading();

}
