package com.example.myhealthguide;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("test noti1", "reemaa");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "100");
        builder.setContentTitle(intent.getStringExtra("title"));
        builder.setContentText(intent.getStringExtra("message"));
        builder.setSmallIcon(R.drawable.heart);
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setVibrate(new long[0]);
        Log.d("test noti2", "reemaa");
        Notification notification = builder.build();
        Log.d("test noti3", "REmma");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("100", "Not", NotificationManager.IMPORTANCE_HIGH));
        }
        notificationManager.notify(m, notification);
    }
}
