package com.atguigu.prohibitpreloaddataviewpager.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by 李金桐 on 2017/2/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MyButton extends TextView {

    private Activity mActivity;

    private boolean isLongClick ;
    private boolean isClick ;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLongClick = true;
                isClick = true;
                longClick();
                break;
            case MotionEvent.ACTION_MOVE:
                //屏蔽长按相应后的事件
                if (isLongClick == true && isClick == false)  return true;

                break;
            case MotionEvent.ACTION_UP:
                //屏蔽长按相应后的事件
                if (isLongClick == true && isClick == false)  return true;

                if (mOnMyClickListener != null && isClick == true) {
                    isLongClick = false;
                    mOnMyClickListener.OnClickListener();
                }
                break;
        }
        return true;
    }

    private void longClick() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    if (mOnMyLongClickListener != null && isLongClick == true) {
                        isClick = false;
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mOnMyLongClickListener.OnLongClickListener();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //点击监听
    private OnMyClickListener mOnMyClickListener;

    public void setOnMyClickListener(OnMyClickListener l) {
        this.mOnMyClickListener = l;
    }

    public interface OnMyClickListener {
        void OnClickListener();
    }

    //长按监听
    private OnMyLongClickListener mOnMyLongClickListener;

    public void setOnMyLongClickListener(OnMyLongClickListener l) {
        this.mOnMyLongClickListener = l;
    }

    public interface OnMyLongClickListener {
        void OnLongClickListener();
    }
}
