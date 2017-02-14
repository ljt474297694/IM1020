package com.atguigu.im1020.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by 李金桐 on 2017/2/14.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ShowToast {
    public static void show(Activity activity, String content){
        Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
    }
    public static void showUIThread(final Activity activity, final String content){
        if(activity==null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(activity,content);
            }
        });

    }
}
