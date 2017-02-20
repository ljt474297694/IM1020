package com.atguigu.im1020.model.bean;

/**
 * Created by 李金桐 on 2017/2/20.
 * QQ: 474297694
 * 功能: xxxx
 */

public class PickInfo {

    private UserInfo userInfo;
    private boolean isCheck;

    public PickInfo() {
    }

    public PickInfo(UserInfo userInfo, boolean isCheck) {
        this.userInfo = userInfo;
        this.isCheck = isCheck;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}