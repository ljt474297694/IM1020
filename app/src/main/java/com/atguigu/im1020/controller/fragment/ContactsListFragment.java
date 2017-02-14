package com.atguigu.im1020.controller.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.activity.AddContactActivity;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.easeui.ui.EaseContactListFragment;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ContactsListFragment extends EaseContactListFragment {


    ImageView contanct_iv_invite;
    LinearLayout ll_new_friends;
    LinearLayout ll_groups;

    @Override
    protected void initView() {
        super.initView();
        View headerView = View.inflate(getActivity(), R.layout.fragment_contact_header, null);
        ll_new_friends = (LinearLayout) headerView.findViewById(R.id.ll_new_friends);
        ll_groups = (LinearLayout) headerView.findViewById(R.id.ll_groups);
        contanct_iv_invite = (ImageView) headerView.findViewById(R.id.contanct_iv_invite);
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
        ll_new_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(getActivity(),"邀请好友");
            }
        });
        ll_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(getActivity(),"群组");
            }
        });
    }
}
