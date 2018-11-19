package com.example.tyanai.myteacher2.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    @WorkerThread
//    public void onMessageReceived(RemoteMessage message) {
//
//        String from = message.getFrom();
//        Map data = message.getData();
//
//        String msg = data.get("data").toString();
//        sendNotification(msg);
//
//    }
    @WorkerThread
    public void onMessageReceived(RemoteMessage message) {

        //誰からか？
        String from = message.getFrom();
        Map data = message.getData();
        String froms = data.get("froms").toString();

        String msg = data.get("data").toString();
        String ttl = data.get("title").toString();
        sendNotification(msg,ttl);
    }
//    private void sendNotification(String message) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 , intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channelId")
//                .setSmallIcon(R.drawable.fav)
//                .setContentTitle("Push通知のタイトル")
//                .setSubText("Push通知のサブタイトル")
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 , notificationBuilder.build());
//    }
    

    private void sendNotification(String message,String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channelId")
                .setSmallIcon(R.drawable.fav)
                .setContentTitle(title)
                .setSubText("Push通知のサブタイトル")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(VISIBILITY_PUBLIC)
                .setVibrate(new long[]{0, 1000})
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }

    @WorkerThread
    public void onDeletedMessages() {
    }
    @WorkerThread
    public void onMessageSent(String var1) {
    }
    @WorkerThread
    public void onSendError(String var1, Exception var2) {
    }
    @WorkerThread
    public void onNewToken(String var1) {
        Log.d("NEW_TOKEN",var1);
    }
}