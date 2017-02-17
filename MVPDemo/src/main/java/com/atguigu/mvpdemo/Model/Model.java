package com.atguigu.mvpdemo.model;

import com.atguigu.mvpdemo.presenter.PresenterInterface;
import com.atguigu.mvpdemo.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: Model 逻辑层
 */

public class Model implements ModelInterface {

    private final PresenterInterface mPresenterInterface;

    public Model(PresenterInterface presenterInterface) {
        this.mPresenterInterface = presenterInterface;
    }

    @Override
    public void getDataFromNet() {

        OkHttpUtils
                .get()
                .url(Constant.URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mPresenterInterface.onError(e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        mPresenterInterface.onSuccess(response);
                    }
                });

    }
}
