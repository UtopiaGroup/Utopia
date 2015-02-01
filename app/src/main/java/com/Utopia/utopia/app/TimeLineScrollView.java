package com.Utopia.utopia.app;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Joe on 14/12/26.
 */
public class TimeLineScrollView extends ScrollView {
    public TimeLineScrollView(Context context) {
        super(context);
        isScrolling = false;
    }

    public TimeLineScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isScrolling = false;
    }

    public TimeLineScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isScrolling = false;
    }
    private boolean isScrolling;


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        isScrolling = true;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(isScrolling)
            if(Math.abs(t - oldt) < 2f) {
                isScrolling = false;
            }
    }
    public boolean isScrolling(){
        return isScrolling;
    }
    public void setScrolling(boolean isScrolling) {
        this.isScrolling = isScrolling;
    }



}
