package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Calendar;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.model.Appointment;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.ActivityAlarmReceiver;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by Thirumal on 20/3/2017.
 */

public class AddReminderAlarmTask extends AsyncTask {

    Context mContext;


    public AddReminderAlarmTask(Context mContext)
    {
        this.mContext=mContext;

    }
    @Override
    protected Object doInBackground(Object... params) {

      if(params!=null) {
          if (params[0] instanceof Medicine) {
              setMedicineReminder((Medicine) params[0],(Reminder)params[1]);
          }else if (params[0] instanceof Appointment) {

              setAppointmentReminder((Appointment) params[0], (Reminder) params[1]);
          }
      }

        return params;
    }

    public void setMedicineReminder(Medicine medicine,Reminder reminder)
    {
        if(reminder!=null) {
            PendingIntent pendingIntent = null;
            AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                if (medicine.isRemind()) {
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(medicine.getReminder().getStartTime());

                    for (int i = 0; i < reminder.getFrequency(); i++) {

                        Intent intent = new Intent(mContext, AlarmReceiver.class);
                        intent.putExtra(DBConstants.MEDICINE_NAME, medicine.getMedicine());
                        intent.putExtra(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
                        intent.putExtra(DBConstants.REMINDER_ID, medicine.getReminderId());
                        intent.putExtra(DBConstants.APP_ID, medicine.getId());
                        intent.putExtra(DBConstants.REMINDER_START_TIME,String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+mContext.getString(R.string.colon)+String.valueOf(calendar.get(Calendar.MINUTE)));
                        calculateReminderTime(calendar);
                        calendar.setTime(calendar.getTime());
                        pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() * 10 + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        calendar.add(Calendar.HOUR_OF_DAY, reminder.getInterval());
                    }


                } else {
                    Intent intent = new Intent(mContext, AlarmReceiver.class);
                    for (int i = 0; i < reminder.getFrequency(); i++) {
                    pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() * 10 + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.cancel(pendingIntent);
                    }

                }
            }

        }

    /**
     * Test the functionality
     *
     * @param appointment
     * @param reminder
     */
    public void setAppointmentReminder(Appointment appointment, Reminder reminder) {
        if (reminder != null) {
            PendingIntent pendingIntent = null;
            AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();

            Intent intent = new Intent(mContext, ActivityAlarmReceiver.class);
            calendar.setTime(reminder.getStartTime());
            calendar.set(Calendar.SECOND, 0);
            intent.putExtra(DBConstants.REMINDER_ID, appointment.getId());
            intent.putExtra(DBConstants.APP_LOCATION, appointment.getLocation());
            intent.putExtra(DBConstants.APP_DESCRIPTION, appointment.getDescription());
//            calculateReminderTime(calendar);
            for (int i = 0; i < reminder.getFrequency(); i++) {
                pendingIntent = PendingIntent.getBroadcast(mContext, appointment.getId()* Constants.APPOINTMENT_BROADCAST_ID * 10 + i, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                calendar.add(Calendar.HOUR_OF_DAY, reminder.getInterval());
            }

        }
        }

        /* calculating Reminder Time to set*/
        public void calculateReminderTime(Calendar calendar)
        {
            Calendar now = Calendar.getInstance();

            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.YEAR,now.get(now.YEAR));
            calendar.set(Calendar.MONTH,now.get(now.MONTH));
            if(now.get(Calendar.HOUR_OF_DAY)>calendar.get(Calendar.HOUR_OF_DAY))
            {
                calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
            }
            else if(now.get(Calendar.HOUR_OF_DAY)==calendar.get(Calendar.HOUR_OF_DAY))
            {
                if(now.get(Calendar.MINUTE)>calendar.get(Calendar.MINUTE)) {
                    calendar.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
                }
                else
                {
                    calendar.set(Calendar.DAY_OF_MONTH,now.get(Calendar.DAY_OF_MONTH));
                }
            }
            else
            {
                calendar.set(Calendar.DAY_OF_MONTH,now.get(Calendar.DAY_OF_MONTH));
            }
        }


}
