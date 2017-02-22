package com.atguigu.im1020.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.adapter.PickAdapter;
import com.atguigu.im1020.model.Model;
import com.atguigu.im1020.model.bean.PickInfo;
import com.atguigu.im1020.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickContactActivity extends AppCompatActivity {

    @Bind(R.id.tv_pick_save)
    TextView tvPickSave;
    @Bind(R.id.lv_pick)
    ListView lvPick;
    private PickAdapter adapter;
    private List<PickInfo> pickInfos;
    private boolean isMember;
    private String groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
        ButterKnife.bind(this);
        getGroupData();
        initView();

        initData();

        initListener();
    }

    private void getGroupData() {
        groupid = getIntent().getStringExtra("groupid");
        isMember = groupid != null;
    }

    private void initListener() {
        lvPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_item_pick_contacts);

                checkBox.setChecked(!checkBox.isChecked());

                pickInfos.get(position).setCheck(checkBox.isChecked());
            }
        });
    }

    private void initData() {
        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactDAO().getContacts();

        pickInfos = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            pickInfos.add(new PickInfo(contacts.get(i), false));
        }

        //如果是群组邀请好友 筛选已经是本群组的成员
        if (isMember) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupid);
            List<String> members = group.getMembers();
            for (int i = 0; i < members.size(); i++) {
                for (int j = 0; j < pickInfos.size(); j++) {
                    if (pickInfos.get(j).getUserInfo().getHxid().equals(members.get(i))) {
                        pickInfos.remove(j);
                    }
                }
            }
        }

        adapter.refresh(pickInfos);

    }

    private void initView() {
        adapter = new PickAdapter(this);
        lvPick.setAdapter(adapter);
    }

    @OnClick(R.id.tv_pick_save)
    public void onClick() {


        List<String> datas = new ArrayList<>();

        if (pickInfos == null) {
            return;
        }


        for (int i = 0; i < pickInfos.size(); i++) {
            if (pickInfos.get(i).isCheck()) {
                datas.add(pickInfos.get(i).getUserInfo().getHxid());
            }
        }
        Intent intent = new Intent();

        intent.putExtra("array", datas.toArray(new String[datas.size()]));
        if (isMember) {
            setResult(2, intent);
        } else {
            setResult(1, intent);
        }

        finish();

    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
