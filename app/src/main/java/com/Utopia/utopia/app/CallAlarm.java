package com.Utopia.utopia.app;

/**
 * Created by chenwenxiao on 15-3-13.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CallAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //显示对话框
        Log.i("DEBUG", "Receive Broadcast");

        intent.setClass(context, AlarmAlert.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}