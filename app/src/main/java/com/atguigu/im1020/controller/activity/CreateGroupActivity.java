package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.atguigu.im1020.R;
import com.atguigu.im1020.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateGroupActivity extends AppCompatActivity {

    @Bind(R.id.et_newgroup_name)
    EditText etNewgroupName;
    @Bind(R.id.et_newgroup_desc)
    EditText etNewgroupDesc;
    @Bind(R.id.cb_newgroup_public)
    CheckBox cbNewgroupPublic;
    @Bind(R.id.cb_newgroup_invite)
    CheckBox cbNewgroupInvite;
    @Bind(R.id.bt_newgroup_create)
    Button btNewgroupCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_newgroup_create)
    public void onClick() {
        if (verify()) {
            boolean checked = cbNewgroupPublic.isChecked();
            startActivity(new Intent(this,PickContactActivity.class));
        }
    }

    private boolean verify() {
        String groupName = etNewgroupName.getText().toString().trim();
        String desc = etNewgroupDesc.getText().toString().trim();

        if (TextUtils.isEmpty(groupName) || TextUtils.isEmpty(desc)) {
            ShowToast.show(this, "群名和群描述不能为空");
            return false;
        }
        return true;
    }
}
