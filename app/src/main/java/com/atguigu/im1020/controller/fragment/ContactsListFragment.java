package com.atguigu.im1020.controller.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.activity.AddContactActivity;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.atguigu.im1020.R.id.ll_groups;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ContactsListFragment extends EaseContactListFragment {


    @Bind(R.id.contanct_iv_invite)
    ImageView contanctIvInvite;
    @Bind(R.id.ll_new_friends)
    LinearLayout llNewFriends;
    @Bind(ll_groups)
    LinearLayout llGroups;

    @Override
    protected void initView() {
        super.initView();
        View headerView = View.inflate(getActivity(), R.layout.fragment_contact_header, null);
        ButterKnife.bind(this, headerView);
        listView.addHeaderView(headerView);
        // 头部图标设置
        titleBar.setRightImageResource(R.drawable.em_add);
        // 加号添加联系人
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        setListener();
    }

    private void setListener() {
        titleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
        llNewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(getActivity(), "邀请好友");
            }
        });
        llGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(getActivity(), "群组");
            }
        });
    }

    /**
     * @param hidden 在Fragment 隐藏与显示切换时调用 如果是隐藏返回true 显示返回false
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            //当显示的时候 可以请求服务器 获取新的数据
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
