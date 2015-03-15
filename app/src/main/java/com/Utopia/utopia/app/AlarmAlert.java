package com.Utopia.utopia.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chenwenxiao on 15-3-14.
 */
public class AlarmAlert extends Activity {
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getExtras().getString("title");
        String value = getIntent().getExtras().getString("value");

        mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
        //显示对话框
        new AlertDialog.Builder(AlarmAlert.this).
                setTitle("闹钟: " + title).//设置标题
                setMessage(value + "\n" + "时间到了！").//设置内容
                setPositiveButton("知道了", new DialogInterface.OnClickListener() {//设置按钮
            public void onClick(DialogInterface dialog, int which) {
                mp.stop();
                mp.release();
                AlarmAlert.this.finish();//关闭Activity
            }
        }).create().show();

    }

}
