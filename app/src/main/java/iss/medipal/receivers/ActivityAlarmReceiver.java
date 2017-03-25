package iss.medipal.receivers;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import iss.medipal.R;
import iss.medipal.constants.DBConstants;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class ActivityAlarmReceiver extends BroadcastReceiver {

    int currentInterval;
    @Override
    public void onReceive(Context context, Intent intent) {

        notifyAppointmentReminder(context,intent);

    }

    public void notifyAppointmentReminder(Context context,Intent intent)
    {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId = intent.getIntExtra(DBConstants.REMINDER_ID, 0);
        String location = intent.getStringExtra(DBConstants.APP_LOCATION);
        String desc = intent.getStringExtra(DBConstants.APP_DESCRIPTION);
        String message = "You have an appointment at  "  + location ;
        currentInterval++;


        Intent myintent=new Intent(context,AppNotificationActionReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, (int)
                AlarmManager.INTERVAL_DAY, myintent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon
                (android.support.v7.appcompat.R.drawable.notification_icon_background).
                setContentTitle(DBConstants.TABLE_APPOINTMENT)
                .setContentText(message + " Description :" + desc).addAction(R.drawable.ic_tick,
                        context.getString(R.string.medication_positive_text),pendingIntent)
                .addAction(R.drawable.ic_clear_black,context.getString(R.string.medication_negative_text),pendingIntent);

        builder.setAutoCancel(Boolean.TRUE);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);

        notificationManager.notify(notifyId, builder.build());
    }
}
