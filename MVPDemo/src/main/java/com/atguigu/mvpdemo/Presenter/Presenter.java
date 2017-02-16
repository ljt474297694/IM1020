package com.atguigu.mvpdemo.presenter;

import com.atguigu.mvpdemo.model.Model;
import com.atguigu.mvpdemo.model.ModelInterface;
import com.atguigu.mvpdemo.view.ViewInterface;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: 控制层
 */

public class Presenter implements  PresenterInterface{

    private final ViewInterface mViewInterface;

    private final ModelInterface mModelInterface;

    public Presenter(ViewInterface viewInterface) {

        this.mViewInterface = viewInterface;

        this.mModelInterface = new Model(this);
    }

    /**
     * 联网获取数据 显示加载中的等待提示控件
     */
    @Override
    public void getDataFromNet() {
        mModelInterface.getDataFromNet();

        mViewInterface.showLoading();
    }

    /**
     * 设置数据 隐藏加载控件
     * @param json
     */
    @Override
    public void onSuccess(String json) {

        mViewInterface.onSuccess(json);

        mViewInterface.hideLoading();
    }
    /**
     *显示错误信息 隐藏加载控件
     */
    @Override
    public void onError(String error) {

        mViewInterface.onError(error);

        mViewInterface.hideLoading();
    }


}
