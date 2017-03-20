package iss.medipal.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import iss.medipal.constants.DBConstants;

/**
 * Created by Naveen on 2/28/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    int currentInterval;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId=intent.getIntExtra(DBConstants.REMINDER_ID,0);
        String medicineName=intent.getStringExtra(DBConstants.MEDICINE_NAME);
        int dosage=intent.getIntExtra(DBConstants.MEDICINE_DOSAGE,0);
        String message="Need to take "+currentInterval+dosage+" pills";
        currentInterval++;

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context).setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background).
                setContentTitle(DBConstants.TABLE_REMINDER)
                .setContentText(message+" of "+medicineName );


        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);
        notificationManager.notify(notifyId,builder.build());
    }
}
