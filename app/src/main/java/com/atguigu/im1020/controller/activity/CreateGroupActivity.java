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
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

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
    private String groupName;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_newgroup_create)
    public void onClick() {
        if (verify()) {
            startActivityForResult(new Intent(this, PickContactActivity.class), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

            Utils.startThread(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 创建群组
                     * @param groupName 群组名称
                     * @param desc 群组简介
                     * @param allMembers 群组初始成员，如果只有自己传空数组即可
                     * @param reason 邀请成员加入的reason
                     * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
                     * @return 创建好的group
                     * @throws HyphenateException
                     */
                    try {
                        String[] arrays = data.getStringArrayExtra("array");

                        if (arrays == null || arrays.length == 0) {
                            return;
                        }
                        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();

                        /**
                         * EMGroupStylePrivateOnlyOwnerInvite——私有群，只有群主可以邀请人；
                         EMGroupStylePrivateMemberCanInvite——私有群，群成员也能邀请人进群；
                         EMGroupStylePublicJoinNeedApproval——公开群，加入此群除了群主邀请，只能通过申请加入此群；
                         EMGroupStylePublicOpenJoin ——公开群，任何人都能加入此群。
                         */
                        option.maxUsers = 200;

                        if (cbNewgroupPublic.isChecked()) {
                            if (cbNewgroupInvite.isChecked()) {
                                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                            } else {
                                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                            }
                        } else {
                            if (cbNewgroupInvite.isChecked()) {
                                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;

                            } else {
                                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                            }
                        }

                        EMClient.getInstance().groupManager()
                                .createGroup(groupName, desc, arrays, "1", option);

                        ShowToast.showUIThread(CreateGroupActivity.this, "创建群成功");

                        finish();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        ShowToast.showUIThread(CreateGroupActivity.this, "创建群失败" + e.getMessage());
                    }
                }
            });


        }
    }

    private boolean verify() {
        groupName = etNewgroupName.getText().toString().trim();
        desc = etNewgroupDesc.getText().toString().trim();

        if (TextUtils.isEmpty(groupName) || TextUtils.isEmpty(desc)) {
            ShowToast.show(this, "群名或群描述不能为空");
            return false;
        }
        return true;
    }
}
