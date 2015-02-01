package com.Utopia.utopia.app;

import android.app.Activity;
import android.content.Intent;
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
public class STDEntry extends Activity{

    private Button button0, button1, button2, button3, buttonOK;

    LinearLayout ll;
    LinearLayout l2;

    NumberPicker picker01, picker02, picker11, picker12;
    EditText editText1, editText2, editText3;

    boolean setEnd = false, setTitle = false, setValue = false, setHint = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_std_entry);



        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        buttonOK = (Button) findViewById(R.id.button_ok);

        ll = (LinearLayout) findViewById(R.id.linear_layout);
        l2 = (LinearLayout) findViewById(R.id.linear_layout2);
        editText1 = (EditText) findViewById(R.id.edit_text1);
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
        /*
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnd = true;
                button0.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle = true;
                button1.setVisibility(View.GONE);
                editText1.setVisibility(View.VISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue = true;
                button2.setVisibility(View.GONE);
                editText2.setVisibility(View.VISIBLE);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHint = true;
                button3.setVisibility(View.GONE);
                editText3.setVisibility(View.VISIBLE);
            }
        });
        */
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long created, modified, begin, end, finish, kind,
                        beginHour, beginMinute, endHour, endMinute;
                byte[] edpv = {};
                String title, value, hint;
                beginHour = picker01.getValue();
                beginMinute = picker02.getValue();
                endHour = picker11.getValue();
                endMinute = picker12.getValue();

                created = TimeUtil.getCurrentTime();
                modified = TimeUtil.getCurrentTime();
                title = editText1.getText().toString();
                if (title.isEmpty()) title = "未命名";
                value = editText2.getText().toString();
                if (value.isEmpty()) value = "";
                hint = editText3.getText().toString();
                if (hint.isEmpty()) hint = "";

                begin = TimeUtil.getToday(created) + 10000 * beginHour + 100 * beginMinute;
                end = TimeUtil.getToday(created) + 10000 * endHour + 100 * endMinute;

                finish = 0;
                kind = DataProviderMetaData.DataTableMetaData.KIND_SCHEDULE;

                Intent intent = new Intent(STDEntry.this, MainActivity.class);
                setResult(RESULT_OK, intent);

                intent.putExtra("created", created);
                intent.putExtra("modified", modified);
                intent.putExtra("title", title);
                intent.putExtra("value", value);
                intent.putExtra("begin", begin);
                intent.putExtra("end", end);
                intent.putExtra("finish", finish);
                intent.putExtra("kind", kind);
                intent.putExtra("myhint", hint);
                intent.putExtra("edpv", edpv);
                finish();
            }
        });
    }


}
