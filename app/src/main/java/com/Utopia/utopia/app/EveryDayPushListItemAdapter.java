package com.Utopia.utopia.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenwenxiao on 14-11-9.
 */

public class EveryDayPushListItemAdapter extends SimpleAdapter {
    int resource;
    Context context;
    List<List<Bundle>> listResource = new ArrayList<List<Bundle>>();

    public EveryDayPushListItemAdapter(Context context, List<Bundle> data, int resource, String[] from, int[] to) {
        super(context, null, resource, from, to);
        this.context = context;
        this.resource = resource;
        long current, last;
        last = -1;
        for (Bundle it : data) {
            current = TimeUtil.getToday(Long.valueOf(it.get("begin").toString()));
            if (current != last) listResource.add(new ArrayList<Bundle>());
            (listResource.get(listResource.size() - 1)).add(it);
            last = current;
        }
    }

    @Override
    public int getCount() {
        return listResource.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(resource, null);
        }
        List<Bundle> currentList = listResource.get(position);
        ((EveryDayPushViewPager) view.findViewById(R.id.EveryDayPushViewPager)).setAdapter(new EveryDayPushViewPagerAdapter(context, currentList));
        return view;
    }
}
