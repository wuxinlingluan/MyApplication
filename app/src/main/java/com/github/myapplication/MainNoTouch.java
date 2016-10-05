package com.github.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/10/5.
 */

public class MainNoTouch extends LinearLayout {
    public MainNoTouch(Context context) {
        this(context,null);
    }

    public MainNoTouch(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MainNoTouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //每个控件都想要处理触摸事件 给内层的控件
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (listener!=null){
          return  listener.onMenuOpen();
        }
        return super.onInterceptTouchEvent(ev);
    }
    private OnMainNoTouchListener listener;
    public  interface OnMainNoTouchListener{
        boolean onMenuOpen();
    }
    public  void setOnMainNoTouchListener(OnMainNoTouchListener listener){
        this.listener=listener;
    }
}

