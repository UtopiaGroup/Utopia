package com.Utopia.utopia.app;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewPagerFragment1 extends Fragment {
    final static int KIND_SCHEDULE = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;

    private ListView lv0;
    private ContentResolver cr;
    private SimpleAdapter sa;
    List<Map<String, Object>> listResource = new ArrayList<Map<String, Object>>();
    ArrayList<ArrayList<Bundle> > all;
    int count;

    public ViewPagerFragment1() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout1,
                container, false);
        lv0 = (ListView) view.findViewById(R.id.page1ListView0);
        cr = getActivity().getContentResolver();
        FromSQLToListView();

        return view;
    }

    public void FromSQLToListView() {
        count = 0;
        Cursor cursor = cr.query(DataProviderMetaData.DataTableMetaData.CONTENT_URI, new String[]{"created", "modified", "title", "value", "begin",
                "end", "finish", "kind", "call"}, "kind = " + KIND_SCHEDULE, null, "begin desc");
        listResource.clear();
        all = new ArrayList<ArrayList<Bundle>>();
        ArrayList<Bundle> day = new ArrayList<Bundle>();

        long last_entry_day = TimeUtil.ENDOfWORLD;

        while (cursor.moveToNext()) {
            long begin_time = cursor.getLong(cursor.getColumnIndex("begin"));
            long begin_day = TimeUtil.getToday(begin_time);
            if (begin_day != last_entry_day) {
                day = new ArrayList<Bundle>();
                all.add(day);
                last_entry_day = begin_day;
            }

            String content = cursor.getString(cursor.getColumnIndex("value"));
            Bundle bundle = new Bundle();
            bundle.putLong("begin",begin_time);
            bundle.putString("content", content);
            day.add(bundle);
            count++;
        }

/*
            ++count;
            Map<String, Object> map = new HashMap<String, Object>();
            String value;
            value = cursor.getString(cursor.getColumnIndex("value"));
            long created;
            created = cursor.getLong(cursor.getColumnIndex("created"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String str1 = sdf.format(new Date(TimeUtil.getMillisFromTime(created)));
            String str2 = TimeUtil.toTime(created);

            map.put("created",str1+str2+" ");
            map.put("value", value);

            listResource.add(map);
            */

        cursor.close();
        if (count > 0) {
            //TODO modify adapter
            sa = new ScheduleListItemAdapter(getActivity(),all,R.layout.schedule_list_item,null,null);
            /*
            sa = new SimpleAdapter(getActivity(), listResource, R.layout.schedule_list_item,
                    new String[]{"created","value"}, new int[]{R.id.time_schedule_list,R.id.content_schedule_list});
                    */
            lv0.setAdapter(sa);
        }
    }
}

