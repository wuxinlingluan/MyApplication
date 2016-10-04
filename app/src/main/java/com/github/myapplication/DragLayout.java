package com.github.myapplication;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/10/1.
 */

public class DragLayout extends FrameLayout {

    private ViewDragHelper helper;
    private View menu;
    private View main;
    private int maxDragRange;


    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化数据
    private void init() {
        helper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == main) {
                if (left < 0) {
                    left = 0;
                } else if (left > maxDragRange) {
                    left = maxDragRange;
                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override //禁止menu移动
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == menu) {
                menu.offsetLeftAndRight(-dx);
                //获取原来main的left
                int oldLeft = main.getLeft();
                int newLeft = oldLeft + dx;
                if (newLeft > maxDragRange) {
                    newLeft = maxDragRange;
                } else if (newLeft < 0) {
                    newLeft = 0;
                }
                int newDx = newLeft - oldLeft;
                main.offsetLeftAndRight(newDx);
            }
        }

        /**
         * 释放视图的回调
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (main.getLeft() < maxDragRange * 0.5f) {
                close();
            } else {
                open();
            }
        }
    };

    private void open() {
        if (helper.smoothSlideViewTo(main, maxDragRange, 0)) {
            invalidate();
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper.continueSettling(true)){
            invalidate();
        }
    }

    private void close() {
        if (helper.smoothSlideViewTo(main, 0, 0)) {
            invalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //让helper接手触摸事件
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //代码健壮性判断
        if (getChildCount() != 2) {
            throw new RuntimeException("you must have two childewn!");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new RuntimeException("your children must be instanceof ViewGroup");
        }
        menu = getChildAt(0);
        main = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxDragRange = (int) (main.getMeasuredWidth() * 0.6f);

    }
}
