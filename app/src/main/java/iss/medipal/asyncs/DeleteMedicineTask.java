package iss.medipal.asyncs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import iss.medipal.constants.Constants;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;
import iss.medipal.ui.interfaces.OnTaskCompleted;

/**
 * Created by Thirumal on 23/3/2017.
 */

public class DeleteMedicineTask extends AsyncTask <Medicine,Void,Integer>{

    private Context mContext;
    private MedicineDao medicineDao;
    private ReminderDao reminderDao;
    private ConsumptionDao consumptionDao;

    public DeleteMedicineTask(Context context)
    {
        this.mContext = context;
        this.medicineDao=new MedicineDaoImpl(context);
        this.reminderDao=new ReminderDaoImpl(context);
        this.consumptionDao = new ConsumptionDaoImpl(context);
    }

    @Override
    protected Integer doInBackground(Medicine... medicines) {

       for(Medicine medicine:medicines)
       {
          if(medicine.getReminderId()>0)
          {
              deleteMedicineReminders(medicine);
              reminderDao.deleteReminder(medicine.getReminderId());
          }
           consumptionDao.deleteConsumtionByMedId(medicine.getId());
           return medicineDao.deleteMedicine(medicine.getId());
       }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    public void deleteMedicineReminders(Medicine medicine)
    {
        if(medicine.getReminder()!=null) {
            AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(mContext, AlarmReceiver.class);
            for (int i = 0; i < medicine.getReminder().getFrequency(); i++) {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, medicine.getId() * 10 + i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                manager.cancel(pendingIntent);
            }
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, Constants.REFILL_BROADCAST_ID + medicine.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
        }
    }
}
