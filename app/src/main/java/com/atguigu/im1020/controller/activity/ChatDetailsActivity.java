package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.adapter.GroupDetailAdapter;
import com.atguigu.im1020.model.bean.UserInfo;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatDetailsActivity extends AppCompatActivity {

    @Bind(R.id.gv_group_detail)
    GridView gvGroupDetail;
    @Bind(R.id.bt_group_detail)
    Button btGroupDetail;
    private String groupid;
    private EMGroup group;
    private GroupDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ButterKnife.bind(this);

        getGroupData();
        initData();
        initListener();
        getGroupMembers();
    }

    private void initListener() {
        adapter.setOnMembersChangeListener(new GroupDetailAdapter.OnMembersChangeListener() {
            @Override
            public void onRemoveGroupMember(final UserInfo userInfo) {
                Utils.startThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().groupManager().removeUserFromGroup(group.getGroupId(), userInfo.getHxid());
                            getGroupMembers();

                            ShowToast.showUIThread(ChatDetailsActivity.this, "移除成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ShowToast.showUIThread(ChatDetailsActivity.this, "移除失败" + e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onAddGroupMember(UserInfo userInfo) {

                startActivityForResult(new Intent(ChatDetailsActivity.this, PickContactActivity.class)
                        .putExtra("groupid", groupid), 2);
            }
        });

        gvGroupDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (adapter.isDeleteModle()) {
                            adapter.setDeleteModle(false);
                        }
                        break;
                }
                return false;
            }
        });


    }

    private void getGroupMembers() {
        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(group.getGroupId());

                    List<String> members = emGroup.getMembers();
                    final ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();

                    for (int i = 0; i < members.size(); i++) {
                        userInfos.add(new UserInfo(members.get(i)));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.refresh(userInfos);
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getGroupData() {

        groupid = getIntent().getStringExtra("groupid");
        group = EMClient.getInstance().groupManager().getGroup(groupid);

    }

    private void initData() {

        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                if (group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
                    btGroupDetail.setText("解散群");
                    btGroupDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.startThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        EMClient.getInstance().groupManager().destroyGroup(groupid);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowToast.show(ChatDetailsActivity.this, "解散成功");
                                                finish();
                                                exitGroup();
                                            }
                                        });
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        ShowToast.showUIThread(ChatDetailsActivity.this, "解散失败");
                                    }
                                }
                            });
                        }
                    });
                } else {
                    btGroupDetail.setText("退群");

                    btGroupDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.startThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        EMClient.getInstance().groupManager().leaveGroup(groupid);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ShowToast.show(ChatDetailsActivity.this, "退群成功");
                                                finish();
                                                exitGroup();
                                            }
                                        });
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        ShowToast.showUIThread(ChatDetailsActivity.this, "退群失败");
                                    }
                                }
                            });
                        }
                    });

                }
            }
        });

        boolean isModify = group.getOwner().equals(EMClient.getInstance().getCurrentUser());
        adapter = new GroupDetailAdapter(this, isModify);

        gvGroupDetail.setAdapter(adapter);


    }

    private void exitGroup() {
//        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
//
//        manager.sendBroadcast(new Intent(Constant.DESTORY_GROUP).putExtra("groupid",groupid));
        EventBus.getDefault().post(groupid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data==null) {
            return;
        }
        final String[] array = data.getStringArrayExtra("array");

        if (array == null) {
            return;
        }
        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().addUsersToGroup(group.getGroupId(), array);
                    ShowToast.showUIThread(ChatDetailsActivity.this, "邀请成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ShowToast.showUIThread(ChatDetailsActivity.this, "邀请失败" + e.getMessage());
                }
            }
        });
    }
}
