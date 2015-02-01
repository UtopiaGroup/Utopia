package com.Utopia.utopia.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2014/5/21 0021.
 * 使用Fragment显示ViewPager中的主要内容
 */
public class ViewPagerFragment3 extends Fragment {
    public ViewPagerFragment3() {
        super();
    }
    LinearLayout everydayPushActivity;
    LinearLayout healthListActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout3,
                container, false);
        everydayPushActivity = (LinearLayout)view.findViewById(R.id.everyday_push_activity);
        healthListActivity = (LinearLayout )view.findViewById(R.id.health_list_activity);
        everydayPushActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),EveryDayPushListActivity.class);
                startActivity(intent);
            }
        });
        healthListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HealthTipListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}