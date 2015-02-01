package com.Utopia.utopia.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2014/5/21 0021.
 * 使用Fragment显示ViewPager中的主要内容
 */

public class SQLiteSchedule extends SQLiteOpenHelper {
    public SQLiteSchedule(Context context) {
        super(context, "Schedule", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Schedule (" +
                "_id integer primary key autoincrement, " +
                "_content varchar(400)" +
                "_beginHour integer" +
                "_beginMinute integer" +
                "_endHour integer" +
                "_endMinute integer" +
                "_finish integer" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NULL
    }
}
