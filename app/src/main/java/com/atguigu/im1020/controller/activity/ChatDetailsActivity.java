package com.atguigu.im1020.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.GridView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

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
                }else {
                    btGroupDetail.setText("退群");
                }
            }
        });
    }

    @OnClick(R.id.bt_group_detail)
    public void onClick() {
        
    }
}
