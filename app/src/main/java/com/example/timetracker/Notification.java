package com.example.timetracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification {
    /**
    String textTitle="";
    String textContent="";

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotification");
    builder.setSmallIcon(R.drawable.notification_icon);
    builder.setContentTitle(textTitle);
    builder.setContentText(textContent);
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    builder.setAutoCancel(true); //removes notif when user taps it

    //must create notification channel before posting any notifs
    //should execute this code as soon as app starts. ok to call repeatedly
    private void createNotificationChannel(){
        //check version because NotificationChannel is only supported on API 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getStirng(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", importance);
            channel.setDescription(description);
            //register channel with system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationManager.createNotificaitonChannel(channel);
        }
    }
     **/
}
