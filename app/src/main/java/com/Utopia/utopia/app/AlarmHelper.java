package com.Utopia.utopia.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by chenwenxiao on 15-3-13.
 */
public class AlarmHelper {

    private Context c;
    private AlarmManager mAlarmManager;

    public AlarmHelper(Context c) {
        this.c = c;
        mAlarmManager = (AlarmManager) c
                .getSystemService(Context.ALARM_SERVICE);
    }

    public void openAlarm(int id, String title, String content, long time) {
        Intent intent = new Intent();
        intent.putExtra("_id", id);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.setClass(c, CallAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(c, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        time = TimeUtil.getSecondFromTime(time);
        //time = calendar.getTimeInMillis();
        Log.v("DEBUG", "time = " + time + "& now is = " +  calendar.getTimeInMillis());
        //1426304880452
        //1426304940309
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

    public void closeAlarm(int id, String title, String content) {
        Intent intent = new Intent();
        intent.putExtra("_id", id);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.setClass(c, CallAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(c, id, intent, 0);
        mAlarmManager.cancel(pi);
    }
}