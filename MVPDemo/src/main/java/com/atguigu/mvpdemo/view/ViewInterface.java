package com.atguigu.mvpdemo.view;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: View层接口 回调显示视图 绑定数据
 */

public interface ViewInterface {

    //设置数据
    void setData(String json);
    //显示错误
    void showError(String error);
    //显加载
    void showLoading();
    //隐藏加载显示
    void hideLoading();
}
