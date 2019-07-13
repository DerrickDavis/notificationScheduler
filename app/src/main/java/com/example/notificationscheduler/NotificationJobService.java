package com.example.notificationscheduler;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)



public class NotificationJobService extends JobService {

    NotificationManager mNotifyManager;

    private  static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";


    //@RequiresApi(api = Build.VERSION_CODES.O)

    @Override
        public boolean onStartJob(JobParameters jobParameters) {

        createNotificationChannel();



            PendingIntent contentPendingIntent = PendingIntent.getActivity(this,0,
                    new Intent(this,MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder
                    (this,PRIMARY_CHANNEL_ID)
                    .setContentTitle("Job service")
                    .setContentText("Your job ran to completion")
                    .setContentIntent(contentPendingIntent)
                    .setSmallIcon(R.drawable.ic_job_running)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);
            mNotifyManager.notify(0,builder.build());

            return false;
        }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }



//@TargetApi(Build.VERSION_CODES.O)

private void createNotificationChannel() {
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel;


    if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.O)
        {
        notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID, "Job service notification",
                            NotificationManager.IMPORTANCE_HIGH );


        notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications from job service");

            mNotifyManager.createNotificationChannel(notificationChannel);
    }

    }
}
