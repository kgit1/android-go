package com.konggit.applocalnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Simple notification (for Android 4+ but only till Android 6, not for Android 7)
        // Creates an explicit intent for an Activity in your app
        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Lunch is ready!")
                .setContentText("It's getting cold...")
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat, "Chat", pendingIntent)
                .build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);*/
        //====================================================================


        //====================================================================
        //Simple notification (for Android 4+ but only till Android 6, not for Android 7)
        // with stack builder object which will contain an artificial back stack
        // for the started Activity

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Ready")
                .setContentText("Steady")
                .setSmallIcon(android.R.drawable.sym_def_app_icon);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationCompat.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationCompat.build());


    }
}
