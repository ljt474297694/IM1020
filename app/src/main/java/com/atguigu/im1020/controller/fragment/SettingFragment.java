package com.atguigu.im1020.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.activity.LoginActivity;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.SpUtils;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class SettingFragment extends Fragment {


    @Bind(R.id.settings_btn_logout)
    Button settingsBtnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingsBtnLogout.setText(("退出登录(" + EMClient.getInstance().getCurrentUser() + ")"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     *
     * @param hidden
     * 在Fragment 隐藏与显示切换时调用 如果是隐藏返回true 显示返回false
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @OnClick(R.id.settings_btn_logout)
    public void onClick() {
        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SpUtils.getInstace().destroy();
                                // 提示
                                ShowToast.show(getActivity(),"退出成功");
                                // 跳转到登录页面
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                // 结束当前页面
                                getActivity().finish();
                            }

                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}