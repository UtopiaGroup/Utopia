package com.Utopia.utopia.app;

import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2014/8/3 0003.
 */
public class TimeUtil {
    public static long ENDOfWORLD = 22000101000000L;
    public static int dayOfMonth[] = new int[]{
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public static long getCurrentTime() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        return second + 100L * minute + 10000L * hour + 1000000L * date + 100000000L * month + 10000000000L * year;
    }

    public static long getToday(long time) {
        return time - time % 1000000L;
    }

    public static long getTomorrow(long time) {
        long today = getToday(time);
        long year = today / 10000000000L;
        today %= 10000000000L;
        long month = today / 100000000L;
        today %= 100000000L;
        long date = today / 1000000L;
        today %= 1000000L;


        if ((year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0))
            dayOfMonth[2] = 29;
        else
            dayOfMonth[2] = 28;
        if (date == dayOfMonth[(int) month]) {
            if (month == 12) {
                year++;
                month = 1;
                date = 1;
            } else {
                month++;
                date = 1;
            }
        } else
            date++;

        return 1000000L * date + 100000000L * month + 10000000000L * year;
    }

    public static long getYesterday(long time) {
        long today = getToday(time);
        long year = today / 10000000000L;
        today %= 10000000000L;
        long month = today / 100000000L;
        today %= 100000000L;
        long date = today / 1000000L;
        today %= 1000000L;

        if ((year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0))
            dayOfMonth[2] = 29;
        else
            dayOfMonth[2] = 28;
        if (date == 1) {
            if (month == 1) {
                year--;
                month = 12;
                date = 31;
            } else {
                month--;
                date = dayOfMonth[(int) month];
            }
        } else
            date--;
        return 1000000L * date + 100000000L * month + 10000000000L * year;
    }

    public static String toTime(long time) {
        long year = time / 10000000000L;
        time %= 10000000000L;
        long month = time / 100000000L;
        time %= 100000000L;
        long date = time / 1000000L;
        time %= 1000000L;
        long hour = time / 10000L;
        time %= 10000L;
        long minute = time / 100L;
        time %= 100L;
        long second = time;

        return (hour < 10 ? "0" : "") + hour + ":" +
                (minute < 10 ? "0" : "") + minute + ":" +
                (second < 10 ? "0" : "") + second;
    }

    public static int toSecond(long time) {
        long year = time / 10000000000L;
        time %= 10000000000L;
        long month = time / 100000000L;
        time %= 100000000L;
        long date = time / 1000000L;
        time %= 1000000L;
        long hour = time / 10000L;
        time %= 10000L;
        long minute = time / 100L;
        time %= 100L;
        long second = time;
        return (int) (hour * 3600 + minute * 60 + second);
    }

    public static String toLunar(long time) {
        long year = time / 10000000000L;
        time %= 10000000000L;
        long month = time / 100000000L;
        time %= 100000000L;
        long date = time / 1000000L;
        Lunar lunar;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, (int) year);
        cal.set(Calendar.MONTH, (int) month - 1);
        cal.set(Calendar.DATE, (int) date);
        SimpleDateFormat sdf = new SimpleDateFormat(" MM月dd日");
        cal.setTimeZone(TimeZone.getDefault());

        lunar = new Lunar(cal);

        return lunar.toString() + sdf.format(cal.getTime());
        //这是一起回传的代码

        //return lunar.toString();
    }
}
