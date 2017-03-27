package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;

/**
 * Created by sreekumar on 3/23/2017.
 *todo reminder
 */

public class EditAppointmentTask extends AsyncTask<Appointment, Void, Long> {


    private Context mContext;
    private AppointmentDao mAppointmentDao;
    private Object[] args;

    public EditAppointmentTask(Context context){

        this.mContext=context;
        this.mAppointmentDao = new AppointmentDaoImpl(context);

    }
    @Override
    protected Long doInBackground(Appointment... params) {
        Reminder reminder = new Reminder(1, 0, params[0].getAppointment());
        long result = mAppointmentDao.updateAppointment(params[0]);
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
