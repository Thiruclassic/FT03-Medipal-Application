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
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by junaidramis on 19/3/17.
 */

public class EditMedicineTask extends AsyncTask<Medicine, Void, Long> {

    private Context mContext;
    private MedicineDao mMedDao;
    private ReminderDao mReminderDao;

    public EditMedicineTask(Context context) {
        this.mContext = context;
        this.mMedDao = new MedicineDaoImpl(context);
        this.mReminderDao = new ReminderDaoImpl(context);
    }

    @Override
    protected Long doInBackground(Medicine... params) {
//        int reminderId = mReminderDao.modifyReminder(params[0].getReminder());
        long result = mMedDao.updateMedicine(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mMedDao != null)
                mMedDao.close();
    }
}