package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.adapter.GroupListAdapter;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroupListActivity extends AppCompatActivity {

    @Bind(R.id.lv_grouplist)
    ListView lvGrouplist;
    private GroupListAdapter adapter;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        ButterKnife.bind(this);
        initView();

        initData();

        initListener();
    }

    private void initView() {
        //添加头布局
        headView = View.inflate(this, R.layout.group_list_head, null);

        lvGrouplist.addHeaderView(headView);

        adapter = new GroupListAdapter(this);
        lvGrouplist.setAdapter(adapter);

    }

    private void initData() {
        Utils.startThread(new Runnable() {
            @Override
            public void run() {
                try {

                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refresh();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refresh() {
        adapter.refresh();
    }

    private void initListener() {

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupListActivity.this,CreateGroupActivity.class));
            }
        });

        lvGrouplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                //跳转到群聊天界面
                Intent intent = new Intent(GroupListActivity.this, ChatActivity.class);

                EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(position - 1);

                intent.putExtra(EaseConstant.EXTRA_USER_ID, emGroup.getGroupId());

                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);

                startActivity(intent);
            }
        });
    }
}
