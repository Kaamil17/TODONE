//package com.example.asusrg.todone;
//
//import android.app.Notification;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//
//public class AlertReciever extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        NotificationHelper notificationHelper = new NotificationHelper(context);
//        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification();
//        notificationHelper.getManager().notify(1, notificationBuilder.build());
//    }
//}
