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
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.ui.activities.MainActivity;

/**
 * Created by Thirumal on 2/28/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isRefillNotifier=intent.getBooleanExtra(DBConstants.TABLE_REMINDER,false);

        if(isRefillNotifier)
        {
            notifyRefillReminder(context,intent);
        }
        else {
            notifyMedicineReminder(context, intent);
        }

    }

    public void notifyMedicineReminder(Context context,Intent intent)
    {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyId = intent.getIntExtra(DBConstants.REMINDER_ID, 0);

            String message =  getMedicineNotificationMessage(context, intent);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background).
                    setContentTitle(DBConstants.TABLE_REMINDER)
                    .setContentText(message);

            builder.setAutoCancel(Boolean.TRUE);

          Intent applicationIntent=new Intent(context, MainActivity.class);
          PendingIntent resultIntent=PendingIntent.getActivity(context,9999999,applicationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
          builder.setContentIntent(resultIntent);
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

                String message = context.getString(R.string.need_to) + Constants.SPACE+context.getString(R.string.refill)+Constants.SPACE + medicineName;


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

    public String getMedicineNotificationMessage(Context context,Intent intent)
    {
        String medicineName = intent.getStringExtra(DBConstants.MEDICINE_NAME);
        int dosage = intent.getIntExtra(DBConstants.MEDICINE_DOSAGE, 0);

        StringBuilder builder=new StringBuilder(context.getString(R.string.need_to));
        Log.d("Triggered",intent.getStringExtra(DBConstants.REMINDER_START_TIME));
        builder.append(Constants.SPACE);
        builder.append(context.getString(R.string.take));
        builder.append(Constants.SPACE);
        builder.append(dosage);
        builder.append(Constants.SPACE);
        if(dosage>1) {
            builder.append(context.getString(R.string.pills));
        }
        else
        {
            builder.append(context.getString(R.string.pill));
        }
        builder.append(Constants.SPACE);
        builder.append(context.getString(R.string.of));
        builder.append(Constants.SPACE);
        builder.append(medicineName);
       /* builder.append("\n");
        builder.append(context.getString(R.string.dosage_time));
        builder.append(Constants.SPACE);
        builder.append(intent.getStringExtra(DBConstants.REMINDER_START_TIME));
*/

        return builder.toString();
    }
}
