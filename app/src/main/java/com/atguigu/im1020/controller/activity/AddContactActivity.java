package com.atguigu.im1020.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.Utils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.atguigu.im1020.R.id.invite_ll_item;

    public class AddContactActivity extends AppCompatActivity {

        @Bind(R.id.invite_btn_search)
        Button inviteBtnSearch;
        @Bind(R.id.invite_et_search)
        EditText inviteEtSearch;
        @Bind(R.id.invite_tv_username)
        TextView inviteTvUsername;
        @Bind(R.id.invite_btn_add)
        Button inviteBtnAdd;
        @Bind(invite_ll_item)
        LinearLayout inviteLlItem;
        private String username;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_contact);
            ButterKnife.bind(this);
        }

        @OnClick({R.id.invite_btn_search, R.id.invite_btn_add})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.invite_btn_search:

                    if(verify()) {
                        inviteLlItem.setVisibility(View.VISIBLE);
                        inviteTvUsername.setText(username);
                    }else{
                        inviteLlItem.setVisibility(View.GONE);

                    }
                    break;
                case R.id.invite_btn_add:

                    Utils.startThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().addContact(username,"添加好友");
                                ShowToast.showUIThread(AddContactActivity.this,"添加好友成功");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                ShowToast.showUIThread(AddContactActivity.this,"添加好友失败");
                            }
                        }
                    });
                    break;
            }
        }
        /**
         * 简单的检验 输入框中的文本数据是否符合规定
         * @return
         */
    private boolean verify() {
        //本地校验
        username = inviteEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ShowToast.showUIThread(this, "查找的账号不能为空");
            return false;
        }
        //服务器校验
        return true;
    }
}
