package iss.medipal.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import iss.medipal.R;
import iss.medipal.constants.DBConstants;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.activities.NotificationAppActivity;


/**
 * Created by sreekumar on 3/26/2017.
 */

public class ActivityAlarmReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        Log.d("In Alarm reciever","hello");
        notifyAppointmentReminder(context,intent);
    }

    public void notifyAppointmentReminder(Context context,Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId = intent.getIntExtra(DBConstants.REMINDER_ID, 0);
        String location = intent.getStringExtra(DBConstants.APP_LOCATION);
        String desc = intent.getStringExtra(DBConstants.APP_DESCRIPTION);
        String message = "You have an appointment at  "  + location ;
        String message2=" "+desc;

        ArrayList<String> textValues = new ArrayList<String>();
        textValues.add(message);
        textValues.add(message2);

      /*  NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setContentTitle(DBConstants.TABLE_APPOINTMENT)
                .setContentText(message);*/
        // Create the Intent to display the info in an Activity
        Intent intentCustom = new Intent(context, MainActivity.class);
        intent.setAction("NotifyMultiXXXX");
        intent.putExtra(NotificationAppActivity.TITLE_EXTRA, DBConstants.TABLE_APPOINTMENT);
        intent.putExtra(NotificationAppActivity.TEXT_VALUES_EXTRA, textValues);

        NotificationCompat.Builder builder = initBasicBuilder(DBConstants.TABLE_APPOINTMENT, message, intentCustom);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);
        builder.setSmallIcon(R.drawable.calendar_clock);
        notificationManager.notify(notifyId, builder.build());

    }
    private NotificationCompat.Builder initBasicBuilder(String title, String text, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.calendar_clock)
                .setContentTitle(title)
                .setContentText(text);

        if (intent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(Boolean.TRUE);
        return builder;
    }
}
