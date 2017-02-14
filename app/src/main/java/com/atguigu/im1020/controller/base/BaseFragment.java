package com.atguigu.im1020.controller.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.im1020.IMApplication;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;


    private Context getmContext() {
        if (getActivity() != null) {
            return getActivity();
        } else if (super.getContext() != null) {
            return super.getContext();
        }
        return IMApplication.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //子类加载试图之前实例化mContext
        mContext = getmContext();
        return initView();
    }

    //子类加载试图
    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //子类加载数据
    protected abstract void initData();
}
