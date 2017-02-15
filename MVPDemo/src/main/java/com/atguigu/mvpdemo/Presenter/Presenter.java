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

    @Override
    public void getDataFromNet() {
        mModelInterface.getDataFromNet();

        mViewInterface.showLoading();
    }

    @Override
    public void onSuccess(String json) {

        mViewInterface.setData(json);

        mViewInterface.hideLoading();
    }

    @Override
    public void onError(String error) {

        mViewInterface.showError(error);

        mViewInterface.hideLoading();
    }


}
