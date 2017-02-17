package com.atguigu.prohibitpreloaddataviewpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 李金桐 on 2017/2/17.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MyButton extends View {
    public MyButton(Context context) {
        this(context,null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       switch (event.getAction()) {
            case  MotionEvent.ACTION_UP:
                if(mOnMyClickListener!=null) {
                    mOnMyClickListener.OnClickListener();
                }
                break;
        }
        return true;
    }

    private OnMyClickListener mOnMyClickListener;

    public void setOnMyClickListener(OnMyClickListener l) {
        this.mOnMyClickListener = l;
    }

    public interface OnMyClickListener{
        void OnClickListener();
    }
}
