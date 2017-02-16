package com.atguigu.im1020.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.activity.AddContactActivity;
import com.atguigu.im1020.controller.activity.InviteActivity;
import com.atguigu.im1020.utils.Constant;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.SpUtils;
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
    private NotifyReceiver notifyReceiver;
    private LocalBroadcastManager lbm;

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
        changePoint();
        //获取监听
        lbm = LocalBroadcastManager.getInstance(getActivity());
        notifyReceiver = new NotifyReceiver();
        lbm.registerReceiver(notifyReceiver,new IntentFilter(Constant.NEW_INVITE_CHANGED));

    }



    private void changePoint() {
        boolean isShow = SpUtils.getInstace().getBoolean(SpUtils.NEW_INVITE, false);
        contanctIvInvite.setVisibility(isShow ? View.VISIBLE : View.GONE);
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
               SpUtils.getInstace().save(SpUtils.NEW_INVITE, false);
                changePoint();
                startActivity( new Intent(getActivity(),InviteActivity.class));
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
        if (!hidden) {
            //当显示的时候 可以请求服务器 获取新的数据
        }
    }


    @Override
    public void onDestroyView() {
        lbm.unregisterReceiver(notifyReceiver);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

     class NotifyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "NotifyReceiver onReceive()");
            changePoint();
        }
    }
}
