package com.Utopia.utopia.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chenwenxiao on 14-11-12.
 */
public class ScheduleListItemAdapter extends SimpleAdapter {
    int resource;
    Context context;
    ArrayList<ArrayList<Bundle>> data;

    public ScheduleListItemAdapter(Context context, ArrayList<ArrayList<Bundle>> data, int resource, String[] from, int[] to) {
        super(context, null, resource, from, to);
        this.data = data;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
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
        TextView time_view = (TextView)view.findViewById(R.id.time_schedule_list);
        TextView content_view = (TextView)view.findViewById(R.id.content_schedule_list);

        ArrayList<Bundle> day = data.get(position);

        Long begin = day.get(0).getLong("begin");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String str1 = sdf.format(new Date(TimeUtil.getMillisFromTime(begin)));
        String str2 = TimeUtil.toTime(begin);

        time_view.setText(str1);

        String content;
        String sbegin;
        String all = new String();
        for (int i = day.size()-1;i>=0;i--) {
            Bundle entry = day.get(i);
            sbegin = TimeUtil.toTime(entry.getLong("begin"));
            content = entry.getString("content");
            all += sbegin+ " "+content+'\n';
        }
        content_view.setText(all);
        /*

        long begin = map.getLong("begin");

        String date = TimeUtil.toLunar(begin);
        String title = map.getString("title");
        String value = map.getString("value");

        ((TextView) view.findViewById(R.id.health_tip_date)).setText(date);
        ((TextView) view.findViewById(R.id.health_tip_value1)).setText(title);
        ((TextView) view.findViewById(R.id.health_tip_value2)).setText(value);
        */
        return view;
    }
}