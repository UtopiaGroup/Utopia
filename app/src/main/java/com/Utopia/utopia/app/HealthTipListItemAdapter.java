package com.Utopia.utopia.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenwenxiao on 14-11-12.
 */
public class HealthTipListItemAdapter extends SimpleAdapter {
    int resource;
    Context context;
    List<Bundle> listResource;

    public HealthTipListItemAdapter(Context context, List<Bundle> data, int resource, String[] from, int[] to) {
        super(context, null, resource, from, to);
        listResource = data;
        this.context = context;
        this.resource = resource;
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
        Bundle map = listResource.get(position);
        long begin = map.getLong("begin");

        String date = TimeUtil.toLunar(begin);
        String title = map.getString("title");
        String value = map.getString("value");
        ((TextView) view.findViewById(R.id.health_tip_date)).setText(date);
        ((TextView) view.findViewById(R.id.health_tip_value1)).setText(title);
        ((TextView) view.findViewById(R.id.health_tip_value2)).setText(value);
        return view;
    }
}
