package com.atguigu.prohibitpreloaddataviewpager.view;

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




    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:

                if (mOnMyClickListener != null) {
                    mOnMyClickListener.OnClickListener();
                }
                break;
        }

        return true;
    }


    //点击监听
    private OnMyClickListener mOnMyClickListener;

    public void setOnMyClickListener(OnMyClickListener l) {
        this.mOnMyClickListener = l;
    }

    public interface OnMyClickListener {
        void OnClickListener();
    }


}
