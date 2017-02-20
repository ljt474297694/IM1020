package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatDetailsActivity extends AppCompatActivity {

    @Bind(R.id.gv_group_detail)
    GridView gvGroupDetail;
    @Bind(R.id.bt_group_detail)
    Button btGroupDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ButterKnife.bind(this);
        
        initData();

    }

    private void initData() {
        final String groupid = getIntent().getStringExtra("groupid");
        
        if(TextUtils.isEmpty(groupid)) {
            return ;
        }

        Utils.startThread(new Runnable() {
            @Override
            public void run() {

                EMGroup group = EMClient.getInstance().groupManager().getGroup(groupid);

                if(group.getOwner().equals(EMClient.getInstance().getCurrentUser())) {
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
                                                ShowToast.show(ChatDetailsActivity.this,"解散成功");
                                                startActivity(new Intent(ChatDetailsActivity.this,GroupListActivity.class));
                                            }
                                        });
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        ShowToast.showUIThread(ChatDetailsActivity.this,"解散失败");
                                    }
                                }
                            });
                        }
                    });
                }else {
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
                                                ShowToast.show(ChatDetailsActivity.this,"退群成功");
                                                startActivity(new Intent(ChatDetailsActivity.this,GroupListActivity.class));
                                            }
                                        });
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                        ShowToast.showUIThread(ChatDetailsActivity.this,"退群失败");
                                    }
                                }
                            });
                        }
                    });

                }
            }
        });
    }

    @OnClick(R.id.bt_group_detail)
    public void onClick() {
        
    }
}
