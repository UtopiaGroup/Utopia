package com.Utopia.utopia.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Joe on 14-10-4.
 */
public class NotePadListItemAdapter extends SimpleAdapter{
    int resource;
    Context context;
    List<Bundle> listResource;

    public NotePadListItemAdapter(Context context, List<Bundle> data, int resource, String[] from, int[] to) {
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
        if(convertView != null) {
            view = convertView;
        }
        else {
            view = inflater.inflate(resource,null);
        }
        Bundle map = listResource.get(position);
        int marginLeft = (int)Long.parseLong(map.get("created").toString());
        int marginRight = (int)Long.parseLong(map.get("end").toString());
        String value = map.get("value").toString();
        if(position % 2 == 0) {
            view.findViewById(R.id.EventTextView0).setLayoutParams(new LinearLayout.LayoutParams(marginLeft, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.findViewById(R.id.EventTextView1).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        else {
            view.findViewById(R.id.EventTextView1).setLayoutParams(new LinearLayout.LayoutParams(marginRight, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.findViewById(R.id.EventTextView0).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        TextView textView = (TextView)view.findViewById(R.id.EventTextViewM);
        textView.setText(value);
        return view;
    }
}
