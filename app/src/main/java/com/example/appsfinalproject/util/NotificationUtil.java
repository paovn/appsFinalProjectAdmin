package com.example.appsfinalproject.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.appsfinalproject.R;


public class NotificationUtil {
    private static final String CHANNEL_ID = "notifications";
    private static final String CHANNEL_NAME = "Notifications";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;

    private static int idCounter = 0;

    public static void createNotification(Context context, String title, String msg, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // si el celular tiene version de android Oreo o superior
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            notificationManager.createNotificationChannel(channel);
            // no hay necesidad de verificar si la notificacion ya existe, android se encarga de esto
            Log.e(">>>", "OREO");
        }

        PendingIntent  pendingIntent = PendingIntent.getActivity(context, idCounter, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // El intent con la actividad que queremos que la notificacion inicie

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pendingIntent) // aqui ponemos el pending intent
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()); // hace que la notificacion sea expandible

        Notification notification = builder.build();
        notificationManager.notify(idCounter, notification);
        Log.e(">>>", "notif" + idCounter);
        idCounter++;
    }
}
