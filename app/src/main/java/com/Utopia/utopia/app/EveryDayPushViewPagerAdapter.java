package com.Utopia.utopia.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 14-11-9.
 */
public class EveryDayPushViewPagerAdapter extends PagerAdapter {
    private List<EveryDayPushView> ViewList = new ArrayList<EveryDayPushView>();
    Context mContext;
    public EveryDayPushViewPagerAdapter(Context context) {
        mContext = context;
    }

    public EveryDayPushViewPagerAdapter(Context context, List<Bundle> mapList) {
        super();
        mContext = context;
        for (Bundle it : mapList) {
            ViewList.add(new EveryDayPushView(context, it));
        }
    }

    public void add(Bundle map) {
        ViewList.add(new EveryDayPushView(mContext, map));
        notifyDataSetChanged();
    }

    public void clear() {
        ViewList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(ViewList.get(position));
        return ViewList.get(position);
        //TODO
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(ViewList.get(position));
    }


    @Override
    public int getCount() {
        return ViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
