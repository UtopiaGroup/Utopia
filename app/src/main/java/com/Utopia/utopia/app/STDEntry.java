package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Utopia.utopia.app.SQL.DataProviderMetaData;

import net.simonvt.numberpicker.NumberPicker;

/**
 * Created by Administrator on 2014/8/31 0031.
 */
public class STDEntry extends Activity {

    private Button button1,buttonOK,buttonX;

    LinearLayout ll;
    LinearLayout l2;

    NumberPicker picker01, picker02, picker11, picker12;
    EditText editText2, editText3;

    ContentResolver cr;
    boolean setEnd;

    long _id = -1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_std_entry);
        setEnd = false;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        buttonOK = (Button) findViewById(R.id.button_ok);
        buttonX = (Button) findViewById(R.id.button_std_disable);
        button1 = (Button) findViewById(R.id.button_std_1);

        ll = (LinearLayout) findViewById(R.id.linear_layout);
        l2 = (LinearLayout) findViewById(R.id.linear_layout2);

        editText2 = (EditText) findViewById(R.id.edit_text2);
        editText3 = (EditText) findViewById(R.id.edit_text3);

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
        editText3.setText("0");

        cr = getContentResolver();
        long created, modified, begin, end, finish, kind, call;
        String title, value;

        begin = TimeUtil.ENDOfWORLD;
        end = TimeUtil.ENDOfWORLD;
        value = "";
        title = "";
        call = 0;

        try {
            _id = getIntent().getExtras().getLong("_id");
            Cursor cursor = cr.query(DataProviderMetaData.DataTableMetaData.CONTENT_URI, new String[]{"created", "modified", "title", "value", "begin", "end", "finish", "kind", "call"},
                    "_id = " + _id, null, null);
            cursor.moveToNext();
            created = cursor.getLong(cursor.getColumnIndex("created"));
            modified = cursor.getLong(cursor.getColumnIndex("modified"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            value = cursor.getString(cursor.getColumnIndex("value"));
            begin = cursor.getLong(cursor.getColumnIndex("begin"));
            end = cursor.getLong(cursor.getColumnIndex("end"));
            finish = cursor.getLong(cursor.getColumnIndex("finish"));
            kind = cursor.getLong(cursor.getColumnIndex("kind"));
            call = cursor.getLong(cursor.getColumnIndex("call"));
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(begin != TimeUtil.ENDOfWORLD) {
            picker01.setValue((int) (begin / 10000 % 100));
            picker02.setValue((int) (begin / 100 % 100));
        }
        else {
            begin = TimeUtil.getCurrentTime();
            picker01.setValue((int) (begin / 10000 % 100));
            picker02.setValue((int) (begin / 100 % 100));
        }

        if(end != TimeUtil.ENDOfWORLD) {
            picker11.setValue((int) (end / 10000 % 100));
            picker12.setValue((int) (end / 100 % 100));
            l2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.GONE);
            setEnd = true;
        }
        else {
            setEnd = false;
            picker11.setValue((int) (begin / 10000 % 100)+1);
            picker12.setValue((int) (begin / 100 % 100));
            l2.setVisibility(View.GONE);
            button1.setVisibility(View.VISIBLE);
        }

        editText2.setText(value);
        editText3.setText(String.valueOf(call));


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                setEnd = true;
            }
        });
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                setEnd = false;
            }
        });


        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long created, modified, begin, end, finish, kind,
                        beginHour, beginMinute, endHour, endMinute, call;
                String title, value;
                beginHour = picker01.getValue();
                beginMinute = picker02.getValue();
                endHour = picker11.getValue();
                endMinute = picker12.getValue();

                created = TimeUtil.getCurrentTime();
                modified = TimeUtil.getCurrentTime();
                value = editText2.getText().toString();
                if (value.isEmpty()) value = "";
                call = Long.valueOf(editText3.getText().toString());

                begin = TimeUtil.getToday(created) + 10000 * beginHour + 100 * beginMinute;
                if(setEnd)
                    end = TimeUtil.getToday(created) + 10000 * endHour + 100 * endMinute;
                else
                    end = TimeUtil.ENDOfWORLD;

                title = "";
                finish = 0;
                kind = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;

                Intent intent = new Intent(STDEntry.this, MainActivity.class);
                if (_id == -1) setResult(RESULT_OK, intent);
                else {
                    setResult(RESULT_FIRST_USER, intent);
                    intent.putExtra("_id", _id);
                }

                intent.putExtra("created", created);
                intent.putExtra("modified", modified);
                intent.putExtra("title", title);
                intent.putExtra("value", value);
                intent.putExtra("begin", begin);
                intent.putExtra("end", end);
                intent.putExtra("finish", finish);
                intent.putExtra("kind", kind);
                intent.putExtra("call", call);
                finish();
            }
        });
    }


}
