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

public class AddMedicineTask extends AsyncTask<Medicine, Void, Long> {

    private Context mContext;
    private MedicineDao mMedDao;
    private ReminderDao mReminderDao;

    public AddMedicineTask(Context context) {
        this.mContext = context;
        this.mMedDao = new MedicineDaoImpl(context);
        this.mReminderDao = new ReminderDaoImpl(context);
    }

    @Override
    protected Long doInBackground(Medicine... params) {
        int reminderId = mReminderDao.addReminder(params[0].getReminder());
        params[0].setReminderId(reminderId);
        params[0].getReminder().setId(reminderId);
        setMedicineReminder(params[0]);
        long result = mMedDao.addMedicine(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mMedDao != null)
                mMedDao.close();
    }

    public void setMedicineReminder(Medicine medicine)
    {
            Intent intent = new Intent(mContext, AlarmReceiver.class);
            intent.putExtra(DBConstants.MEDICINE_NAME, medicine.getMedicine());
            intent.putExtra(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(medicine.getReminder().getStartTime());
            calendar.set(Calendar.SECOND, 0);
            AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}