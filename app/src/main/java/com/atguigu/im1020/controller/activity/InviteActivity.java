package com.atguigu.im1020.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.adapter.InviteAdapter;
import com.atguigu.im1020.model.Model;
import com.atguigu.im1020.model.bean.InvitationInfo;
import com.atguigu.im1020.utils.Constant;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {

    @Bind(R.id.lv_invite)
    ListView lvInvite;
    private List<InvitationInfo> invitations;
    private InviteAdapter mInviteAdapter;

    private InviteAdapter.OnInviteListener mOnInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InvitationInfo info) {
            // 接受按钮的点击事件
            Utils.startThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(info.getUserInfo().getHxid());

                        Model.getInstance().getDbManager().getInvitationDAO()
                                .updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT, info.getUserInfo().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                                ShowToast.show(InviteActivity.this, "接受成功");
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        ShowToast.showUIThread(InviteActivity.this, "接受失败" + e.getMessage());
                    }
                }
            });
        }

        @Override
        public void onReject(final InvitationInfo info) {
            // 拒绝
            Utils.startThread(new Runnable() {
                @Override
                public void run() {
                    try {

                        EMClient.getInstance().contactManager().declineInvitation(info.getUserInfo().getHxid());

                        Model.getInstance().getDbManager()
                                .getInvitationDAO().removeInvitation(info.getUserInfo().getHxid());
                        Model.getInstance().getDbManager()
                                .getContactDAO().deleteContactByHxId(info.getUserInfo().getHxid());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                                ShowToast.show(InviteActivity.this, "拒绝成功");
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        ShowToast.showUIThread(InviteActivity.this, "拒绝失败" + e.getMessage());
                    }

                }
            });
        }
    };
    private LocalBroadcastManager manager;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {

        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, new IntentFilter(Constant.NEW_INVITE_CHANGED));

        mInviteAdapter = new InviteAdapter(this, mOnInviteListener);
        lvInvite.setAdapter(mInviteAdapter);
        //刷新方法
        refresh();
    }

    private void refresh() {
        mInviteAdapter.refresh();
    }

    private void initListener() {

    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            manager.unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}

