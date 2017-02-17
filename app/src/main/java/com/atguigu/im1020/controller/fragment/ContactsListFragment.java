package com.atguigu.im1020.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.activity.AddContactActivity;
import com.atguigu.im1020.controller.activity.ChatActivity;
import com.atguigu.im1020.controller.activity.InviteActivity;
import com.atguigu.im1020.model.Model;
import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.utils.Constant;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.SpUtils;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        getDataFromHX();
        setListener();
        changePoint();
        //获取监听
        lbm = LocalBroadcastManager.getInstance(getActivity());
        notifyReceiver = new NotifyReceiver();
        lbm.registerReceiver(notifyReceiver, new IntentFilter(Constant.NEW_INVITE_CHANGED));
        lbm.registerReceiver(notifyReceiver, new IntentFilter(Constant.CONTACT_CHANGED));

    }

    private void getDataFromHX() {
        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> hxIds = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    List<UserInfo> userInfos = new ArrayList<UserInfo>();

                    UserInfo userInfo;
                    for (int i = 0; i < hxIds.size(); i++) {
                        userInfo = new UserInfo(hxIds.get(i));
                        userInfos.add(userInfo);
                    }
                    Model.getInstance().getDbManager().getContactDAO().saveContacts(userInfos, true);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshContact();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshContact() {
        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactDAO().getContacts();
        if (contacts == null) {
            return;
        }
        Map<String, EaseUser> maps = new HashMap<>();
        EaseUser easeUser;
        for (int i = 0; i < contacts.size(); i++) {
            easeUser = new EaseUser(contacts.get(i).getHxid());
            maps.put(contacts.get(i).getHxid(), easeUser);
        }
        setContactsMap(maps);
        refresh();
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
                startActivity(new Intent(getActivity(), InviteActivity.class));
            }
        });
        llGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast.show(getActivity(), "群组");
            }
        });

        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(getActivity(),ChatActivity.class));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                List<UserInfo> contacts = Model.getInstance().getDbManager().getContactDAO().getContacts();
                showMenu(contacts.get(position - 1));
                return true;
            }
        });
    }

    private void showMenu(final UserInfo userInfo) {
        new AlertDialog.Builder(getActivity())
                .setMessage("你确定要删除吗")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            EMClient.getInstance().contactManager().deleteContact(userInfo.getHxid());
                            Model.getInstance().getDbManager().getContactDAO().deleteContactByHxId(userInfo.getHxid());
                            refreshContact();
                            ShowToast.show(getActivity(),"删除成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ShowToast.show(getActivity(),"删除失败" + e.getMessage());
                        }

                    }
                }).show();

    }

    /**
     * @param hidden 在Fragment 隐藏与显示切换时调用 如果是隐藏返回true 显示返回false
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //当显示的时候 可以请求服务器 获取新的数据
            refreshContact();
        }
    }


    @Override
    public void onDestroyView() {
        lbm.unregisterReceiver(notifyReceiver);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }


    class NotifyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.NEW_INVITE_CHANGED:
                    changePoint();
                    break;
                case Constant.CONTACT_CHANGED:
                    refreshContact();
                    break;
            }
        }
    }
}
