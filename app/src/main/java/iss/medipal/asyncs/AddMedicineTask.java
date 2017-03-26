package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import iss.medipal.MediPalApplication;
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

public class AddMedicineTask extends AsyncTask<Medicine, Void, Reminder> {

    private Context mContext;
    private MedicineDao mMedDao;
    private ReminderDao mReminderDao;
    private Object[] args;
    private OnTaskCompleted mListener;

    public AddMedicineTask(Context context, OnTaskCompleted listener) {
        this.mContext = context;
        this.mMedDao = new MedicineDaoImpl(context);
        this.mReminderDao = new ReminderDaoImpl(context);
        this.mListener = listener;
    }

    @Override
    protected Reminder doInBackground(Medicine... params) {
        Reminder reminder= mReminderDao.addReminder(params[0].getReminder());
        if(reminder!=null) {
            params[0].setReminderId(reminder.getId());
            params[0].getReminder().setId(reminder.getId());
        }
        int result = mMedDao.addMedicine(params[0]);
        for (Medicine med : MediPalApplication.getPersonStore().getmPersonalBio().getMedicines()) {
            if (med.equals(params[0])) {
                med.setId(result);
            }
        }
        args=new Object[]{params[0],reminder};
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