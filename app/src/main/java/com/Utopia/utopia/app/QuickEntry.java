package com.Utopia.utopia.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import net.simonvt.numberpicker.NumberPicker;

/**
 * Created by Joe on 14-8-15.
 */
public class QuickEntry extends AlertDialog {

    Button button1;
    Button button2;
    Button button3;
    LinearLayout ll;
    LinearLayout l2;
    EditText editText;
    NumberPicker picker01, picker02;
    NumberPicker picker11, picker12;
    boolean setEnd = false, setValue = false,confirmed = false;

    int hour = 0,minute = 0;

    public QuickEntry(Context context) {
        super(context);
    }

    public QuickEntry(Context context,int hour,int minute) {
        super(context);
        this.hour = hour;
        this.minute = minute;
    }

    Bundle getContent()
    {
        long created, modified, begin, end, finish, kind,
                beginHour, beginMinute, endHour, endMinute, call;
        String title, value;
        beginHour = picker01.getValue();
        beginMinute = picker02.getValue();
        endHour = picker11.getValue();
        endMinute = picker12.getValue();

        Bundle map = new Bundle();

        created = TimeUtil.getCurrentTime();
        modified = TimeUtil.getCurrentTime();
        if (setValue) value = editText.getText().toString();
        else value = "";

        title = "未命名";

        begin = TimeUtil.getToday(created) + 10000 * beginHour + 100 * beginMinute;
        if (setEnd) end = TimeUtil.getToday(created) + 10000 * endHour + 100 * endMinute;
        else end = TimeUtil.ENDOfWORLD;

        call = 0;

        finish = 0;
        if(confirmed)
            kind = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;
        else
            kind = DataProviderMetaData.DataTableMetaData.KIND_NONE;

        map.putLong("created", created);
        map.putLong("modified", modified);
        map.putString("title", title);
        map.putString("value", value);
        map.putLong("begin", begin);
        map.putLong("end", end);
        map.putLong("finish", finish);
        map.putLong("kind", kind);
        map.putLong("call", call);

        return map;
        //dismiss : ((ViewPagerFragment2)(((MainActivity) getOwnerActivity()).fragmentList.get(2))).addEvent(map);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.dialog_quick_entry);


        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        ll = (LinearLayout) findViewById(R.id.linear_layout);
        l2 = (LinearLayout) findViewById(R.id.linear_layout2);
        editText = (EditText) findViewById(R.id.edit_text);
        picker01 = (NumberPicker) findViewById(R.id.picker01);
        picker02 = (NumberPicker) findViewById(R.id.picker02);
        picker11 = (NumberPicker) findViewById(R.id.picker11);
        picker12 = (NumberPicker) findViewById(R.id.picker12);
        picker01.setMinValue(0);
        picker01.setMaxValue(23);
        picker02.setMinValue(0);
        picker02.setMaxValue(59);
        picker11.setMinValue(0);
        picker11.setMaxValue(23);
        picker12.setMinValue(0);
        picker12.setMaxValue(59);

        //TODO get the "time"

        picker01.setValue(hour);
        picker02.setValue(minute);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnd = true;
                button1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue = true;
                button2.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO retrieve and store the data when clicked;
                confirmed = true;
                dismiss();
            }
        });
    }
}
