package com.example.tyanai.myteacher2.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyInstanceIDListenerService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }
}
