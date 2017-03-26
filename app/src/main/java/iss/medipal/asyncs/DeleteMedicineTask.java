package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.ui.interfaces.OnTaskCompleted;

/**
 * Created by Thirumal on 23/3/2017.
 */

public class DeleteMedicineTask extends AsyncTask <Medicine,Void,Integer>{

    Context mContext;
    MedicineDao medicineDao;
    ReminderDao reminderDao;
    ConsumptionDao consumptionDao;
    OnTaskCompleted mCallback;

    public DeleteMedicineTask(Context context, OnTaskCompleted callback)
    {
        this.mContext = context;
        this.medicineDao=new MedicineDaoImpl(context);
        this.reminderDao=new ReminderDaoImpl(context);
        this.consumptionDao = new ConsumptionDaoImpl(context);
        this.mCallback = callback;
    }

    @Override
    protected Integer doInBackground(Medicine... medicines) {

       for(Medicine medicine:medicines)
       {
          if(medicine.getReminderId()>0)
          {
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
        if(mCallback !=null) {
            mCallback.onTaskCompleted();
        }
    }
}
