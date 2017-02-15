package com.atguigu.im1020.model.bean;

/**
 * Created by 李金桐 on 2017/2/15.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GroupInfo {

    private String groupName; //群组名称
    private String groupid;  //群组ID
    private String invitePerson; //邀请人

    public GroupInfo() {
    }

    public GroupInfo(String groupName, String groupid, String invitePerson) {
        this.groupName = groupName;
        this.groupid = groupid;
        this.invitePerson = invitePerson;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(String invitePerson) {
        this.invitePerson = invitePerson;
    }
}
