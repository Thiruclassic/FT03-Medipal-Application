package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Calendar;

import iss.medipal.constants.DBConstants;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by Thirumal on 20/3/2017.
 */

public class AddReminderAlarmTask extends AsyncTask {

    Context mContext;
    Reminder reminder;

    public AddReminderAlarmTask(Context mContext)
    {
        this.mContext=mContext;
        this.reminder=reminder;
    }
    @Override
    protected Object doInBackground(Object... params) {

      if(params!=null) {
          if (params[0] instanceof Medicine) {
              setMedicineReminder((Medicine) params[0],(Reminder)params[1]);
          }
      }

        return params;
    }

    public void setMedicineReminder(Medicine medicine,Reminder reminder)
    {
        PendingIntent pendingIntent = null;
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra(DBConstants.MEDICINE_NAME, medicine.getMedicine());
        intent.putExtra(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
        intent.putExtra(DBConstants.REMINDER_ID, medicine.getReminderId());
        intent.putExtra(DBConstants.APP_ID, medicine.getId());

        if(medicine.isRemind()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(medicine.getReminder().getStartTime());
            calendar.set(Calendar.SECOND, 0);




            int interval = 0;
            for (int i = 0; i < reminder.getFrequency(); i++) {
                pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                calendar.add(Calendar.HOUR_OF_DAY, reminder.getInterval());
            }
        }
        else
        { for (int i = 0; i < reminder.getFrequency(); i++) {
            pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.cancel(pendingIntent);

        }

        }

    }
}
