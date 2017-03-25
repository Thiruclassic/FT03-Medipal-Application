package iss.medipal.receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.widget.RemoteViews;
import android.widget.Toast;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;

/**
 * Created by Thirumal on 2/28/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    int currentInterval;
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isRefillNotifier=intent.getBooleanExtra(DBConstants.TABLE_REMINDER,false);
        if(isRefillNotifier)
        {
            notifyRefillReminder(context,intent);
        }
        else
        {
            notifyMedicineReminder(context,intent);
        }

    }

    public void notifyMedicineReminder(Context context,Intent intent)
    {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyId = intent.getIntExtra(DBConstants.REMINDER_ID, 0);
            String medicineName = intent.getStringExtra(DBConstants.MEDICINE_NAME);
            int dosage = intent.getIntExtra(DBConstants.MEDICINE_DOSAGE, 0);
            String message = "Need to take "  + dosage + " pills";
            currentInterval++;


        Intent myintent=new Intent(context,NotificationActionReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, (int)AlarmManager.INTERVAL_DAY, myintent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background).
                    setContentTitle(DBConstants.TABLE_REMINDER)
                    .setContentText(message + " of " + medicineName).addAction(R.drawable.ic_tick,context.getString(R.string.medication_positive_text),pendingIntent)
                    .addAction(R.drawable.ic_clear_black,context.getString(R.string.medication_negative_text),pendingIntent);

            builder.setAutoCancel(Boolean.TRUE);
            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(notificationSound);

            notificationManager.notify(notifyId, builder.build());

    }

    public void notifyRefillReminder(Context context,Intent intent)
    {
        boolean isRefillReminder=intent.getBooleanExtra(Constants.REMINDER_TAB_3,false);
        if(isRefillReminder) {

        int medicineId=intent.getIntExtra(DBConstants.APP_ID,0);
            int quantity=intent.getIntExtra(DBConstants.APP_ID,0);
            int threshold=intent.getIntExtra(DBConstants.MEDICINE_THRESHOLD,0);

            if(quantity<threshold) {
                int notifyId = Constants.REFILL_BROADCAST_ID + medicineId;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                String medicineName = intent.getStringExtra(DBConstants.MEDICINE_NAME);

                String message = "Need to Refill " + medicineName;
                currentInterval++;

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background).
                        setContentTitle(DBConstants.TABLE_REMINDER)
                        .setContentText(message);


                Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(notificationSound);
                notificationManager.notify(notifyId, builder.build());
            }
        }
    }

    public void setListeners()
    {

    }
}
