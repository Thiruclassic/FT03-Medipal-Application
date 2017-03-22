package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Calendar;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.model.Medicine;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by Sreekumar on 3/12/2017.
 */

public class AddAppointmentTask  extends AsyncTask<Appointment, Void, Long> {
;
    private Context mContext;
    private AppointmentDaoImpl mAppointmentDao;
    private ReminderDao mReminderDao;

    public AddAppointmentTask(Context context){
        this.mContext=mContext;
        this.mAppointmentDao =new AppointmentDaoImpl(context);
        this.mReminderDao=new ReminderDaoImpl(context);

    }
    @Override
    protected Long doInBackground(Appointment... params) {
        int reminderId = mReminderDao.addReminder(params[0].getReminder()).getId();
        params[0].setReminderId(reminderId);
        params[0].getReminder().setId(reminderId);
        setAppointmentReminder(params[0]);
        long result = mAppointmentDao.addAppointment(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mAppointmentDao != null)
                mAppointmentDao.close();
    }

    public void setAppointmentReminder(Appointment appointmentReminder)
    {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra(DBConstants.APP_LOCATION, appointmentReminder.getLocation());
        intent.putExtra(DBConstants.APP_DESCRIPTION, appointmentReminder.getDescription());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointmentReminder.getReminder().getStartTime());
        calendar.set(Calendar.SECOND, 0);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
