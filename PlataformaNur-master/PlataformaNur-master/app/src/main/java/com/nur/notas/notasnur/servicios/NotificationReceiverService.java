package com.nur.notas.notasnur.servicios;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nur.notas.notasnur.R;
import com.nur.notas.notasnur.TabBarActivity;

import androidx.core.app.NotificationCompat;

public class NotificationReceiverService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() == 0)
            return;

        Intent intent = new Intent(this, TabBarActivity.class);

        if (remoteMessage.getData().containsKey("tipo") && remoteMessage.getData().get("tipo").equals("mensaje")) {
            String mensaje = remoteMessage.getData().get("mensaje");
            intent.putExtra("MENSAJE", mensaje);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("FCM Message")
                .setContentText("Tiene un nuevo Mensaje")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(android.R.drawable.ic_input_add)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        int i = 4;
    }

//    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//        Log.i("com.nur.notas.notasnur", "onNewToken(" + s + ")");
//        // TODO: actualizar el token sólo si tengo una sesión iniciada
//    }

}
