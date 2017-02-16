package com.atguigu.im1020.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.model.Model;
import com.atguigu.im1020.model.bean.InvitationInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {

    @Bind(R.id.lv_invite)
    ListView lvInvite;
    private List<InvitationInfo> invitations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        //刷新方法
        refresh();
    }

    private void refresh() {
        invitations = Model.getInstance().getDbManager().getInvitationDAO().getInvitations();
    }

    private void initListener() {
    }

}
