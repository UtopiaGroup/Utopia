package com.Utopia.utopia.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Joe on 14-9-7.
 */
public class HealthTipListItem extends LinearLayout {
    public HealthTipListItem(Context context) {
        super(context);
        init();
    }

    public HealthTipListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HealthTipListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.health_tip_list_item, this);
    }

}
