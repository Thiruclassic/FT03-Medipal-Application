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
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by Sreekumar on 3/12/2017.
 */

public class AddAppointmentTask extends AsyncTask<Appointment, Void, Long> {

    private Context mContext;
    private AppointmentDaoImpl mAppointmentDao;
    private Object[] args;

    public AddAppointmentTask(Context context) {
        this.mContext = context;
        this.mAppointmentDao = new AppointmentDaoImpl(context);

    }

    @Override
    protected Long doInBackground(Appointment... params) {
        Reminder reminder = new Reminder(1, 0, params[0].getAppointment());
        long result = mAppointmentDao.addAppointment(params[0]);
        args = new Object[]{params[0], reminder};
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mAppointmentDao != null)
                mAppointmentDao.close();
        AddReminderAlarmTask mAddReminderAlarmTask = new AddReminderAlarmTask(mContext);
        mAddReminderAlarmTask.execute(args);
    }

}
