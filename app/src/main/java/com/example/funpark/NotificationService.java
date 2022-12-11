package com.example.funpark;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Toast.makeText(this,"notification!!!",Toast.LENGTH_SHORT);
    }
}
