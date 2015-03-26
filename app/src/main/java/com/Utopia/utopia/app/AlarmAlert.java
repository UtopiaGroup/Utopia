package com.Utopia.utopia.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chenwenxiao on 15-3-14.
 */
public class AlarmAlert extends Activity {
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String content = getIntent().getExtras().getString("content");

        mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
        //显示对话框

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH时mm分ss秒 E");// 设置你想要的格式
        String dateStr = df.format(calendar.getTime());

        new AlertDialog.Builder(AlarmAlert.this).
                setTitle("闹钟：" + dateStr).//设置标题
                setMessage(content + "\n" + "时间到了！").//设置内容
                setPositiveButton("知道了", new DialogInterface.OnClickListener() {//设置按钮
            public void onClick(DialogInterface dialog, int which) {
                mp.stop();
                mp.release();
                AlarmAlert.this.finish();//关闭Activity
            }
        }).create().show();

    }

}
