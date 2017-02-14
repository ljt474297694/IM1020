package com.atguigu.im1020.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class SettingFragment extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView = new TextView(getActivity());
        textView.setText("设置");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
