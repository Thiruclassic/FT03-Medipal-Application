package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.Calendar;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;
import iss.medipal.ui.interfaces.OnTaskCompleted;

/**
 * Created by junaidramis on 19/3/17.
 */

public class EditMedicineTask extends AsyncTask<Medicine, Void, Reminder> {

    private Context mContext;
    private MedicineDao mMedDao;
    private ReminderDao mReminderDao;
    private AddReminderAlarmTask mAddReminderAlarmTask;
    private OnTaskCompleted mListener;
    private Object[] args;

    public EditMedicineTask(Context context, OnTaskCompleted listener) {
        this.mContext = context;
        this.mMedDao = new MedicineDaoImpl(context);
        this.mReminderDao = new ReminderDaoImpl(context);
        this.mListener = listener;
    }

    @Override
    protected Reminder doInBackground(Medicine... params) {
        Reminder reminder=null;

        if (params[0].getReminderId() > 0) {
            reminder=mReminderDao.modifyReminder(params[0].getReminder());
        }
        else
        {
            reminder=mReminderDao.addReminder(params[0].getReminder());
            params[0].setReminderId(reminder.getId());
        }
            long result = mMedDao.updateMedicine(params[0]);
            args = new Object[]{params[0], reminder};
        return reminder;
    }

    @Override
    protected void onPostExecute(Reminder result) {
            if (mMedDao != null)
                mMedDao.close();
        if(mListener !=null) {
            mListener.onTaskCompleted();
        }
        AddReminderAlarmTask mAddReminderAlarmTask=new AddReminderAlarmTask(mContext);
        mAddReminderAlarmTask.execute(args);
    }



}