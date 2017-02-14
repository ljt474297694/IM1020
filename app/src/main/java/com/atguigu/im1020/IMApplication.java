package com.atguigu.im1020;

import android.app.Application;

import com.atguigu.im1020.modle.Modle;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class IMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initHXSdk();
        initModle();
    }

    private void initModle() {
        Modle.getInstance().init(this);

    }

    private void initHXSdk() {
        EMOptions options = new EMOptions();
        //是否总是接受邀请
        options.setAcceptInvitationAlways(false);
        //是否总是接受群邀请
        options.setAutoAcceptGroupInvitation(false);

        EaseUI.getInstance().init(this, options);
    }
    private static IMApplication mIMApplication;
    public static IMApplication getInstance(){
        if(mIMApplication==null) {
            mIMApplication = new IMApplication();
        }
        return mIMApplication;
    }
}
