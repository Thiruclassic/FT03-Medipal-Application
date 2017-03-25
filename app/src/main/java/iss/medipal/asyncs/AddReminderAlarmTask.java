package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Calendar;

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
    Reminder reminder;

    public AddReminderAlarmTask(Context mContext) {
        this.mContext = mContext;
        this.reminder = reminder;
    }

    @Override
    protected Object doInBackground(Object... params) {

        if (params != null) {
            if (params[0] instanceof Medicine) {
                setMedicineReminder((Medicine) params[0], (Reminder) params[1]);
            } else if (params[0] instanceof Appointment) {

                setAppointmentReminder((Appointment) params[0], (Reminder) params[1]);
            }
        }

        return params;
    }

    public void setMedicineReminder(Medicine medicine, Reminder reminder) {
        if (reminder != null) {
            PendingIntent pendingIntent = null;
            AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();


            Intent intent = new Intent(mContext, AlarmReceiver.class);
            if (medicine.isRemind()) {
                calendar.setTime(medicine.getReminder().getStartTime());
                calendar.set(Calendar.SECOND, 0);


                intent.putExtra(DBConstants.MEDICINE_NAME, medicine.getMedicine());
                intent.putExtra(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
                intent.putExtra(DBConstants.REMINDER_ID, medicine.getReminderId());
                intent.putExtra(DBConstants.APP_ID, medicine.getId());

                int interval = 0;
                for (int i = 0; i < reminder.getFrequency(); i++) {
                    pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() * 10 + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    calendar.add(Calendar.HOUR_OF_DAY, reminder.getInterval());
                }


            } else {
                for (int i = 0; i < reminder.getFrequency(); i++) {
                    pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() * 10 + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    manager.cancel(pendingIntent);
                }

            }
        }

    }

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

            for (int i = 0; i < reminder.getFrequency(); i++) {
                pendingIntent = PendingIntent.getBroadcast(mContext, appointment.getId() * 10 + i, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                calendar.add(Calendar.HOUR_OF_DAY, reminder.getInterval());
            }

        }


    }


}

