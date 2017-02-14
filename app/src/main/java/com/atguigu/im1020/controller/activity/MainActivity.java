package com.atguigu.im1020.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.im1020.R;
import com.atguigu.im1020.controller.fragment.ContactsListFragment;
import com.atguigu.im1020.controller.fragment.ConversationFragment;
import com.atguigu.im1020.controller.fragment.SettingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private Fragment tempFragment;
    private SettingFragment mSettingFragment;
    private ContactsListFragment mContactsListFragment;
    private ConversationFragment mConversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case  R.id.rb_main_chat:
                        switchFragment(mConversationFragment);
                        break;
                    case  R.id.rb_main_contact:
                        switchFragment(mContactsListFragment);
                        break;
                    case  R.id.rb_main_setting:
                        switchFragment(mSettingFragment);
                        break;
                }

            }
        });
        rgMain.check(R.id.rb_main_chat);
    }

    /**
     *  切换Fragment的方法 判断Fragment是否添加过 如果添加过就显示 之前的隐藏
     */
    private void switchFragment(Fragment currentFragment) {
        if (tempFragment != currentFragment) {
            if (currentFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (currentFragment.isAdded()) {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.show(currentFragment);
                } else {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.add(R.id.fl_main, currentFragment);
                }
                ft.commit();
                tempFragment = currentFragment;
            }
        }
    }
    private void initData() {
        // 创建三个fragment
        mConversationFragment = new ConversationFragment();
        mContactsListFragment = new ContactsListFragment();
        mSettingFragment = new SettingFragment();
    }
}
