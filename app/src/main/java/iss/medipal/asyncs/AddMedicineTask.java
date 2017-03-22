package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by junaidramis on 19/3/17.
 */

public class AddMedicineTask extends AsyncTask<Medicine, Void, Reminder> {

    private Context mContext;
    private MedicineDao mMedDao;
    private ReminderDao mReminderDao;
    private Object[] args;

    public AddMedicineTask(Context context) {
        this.mContext = context;
        this.mMedDao = new MedicineDaoImpl(context);
        this.mReminderDao = new ReminderDaoImpl(context);
    }

    @Override
    protected Reminder doInBackground(Medicine... params) {
        Reminder reminder= mReminderDao.addReminder(params[0].getReminder());
        params[0].setReminderId(reminder.getId());
        params[0].getReminder().setId(reminder.getId());
        long result = mMedDao.addMedicine(params[0]);
        args=new Object[]{params[0],reminder};
        return reminder;
    }

    @Override
    protected void onPostExecute(Reminder result) {
            if (mMedDao != null)
                mMedDao.close();
        AddReminderAlarmTask mAddReminderAlarmTask=new AddReminderAlarmTask(mContext);
        mAddReminderAlarmTask.execute(args);

    }


}