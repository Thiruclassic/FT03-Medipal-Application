package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;

/**
 * Created by Thirumal on 23/3/2017.
 */

public class DeleteMedicineTask extends AsyncTask <Medicine,Void,Integer>{

    Context mContext;
    MedicineDao medicineDao;
    ReminderDao reminderDao;

    public DeleteMedicineTask(Context context)
    {
        this.medicineDao=new MedicineDaoImpl(context);
        this.reminderDao=new ReminderDaoImpl(context);
    }

    @Override
    protected Integer doInBackground(Medicine... medicines) {

       for(Medicine medicine:medicines)
       {
          if(medicine.getReminderId()>0)
          {
              reminderDao.deleteReminder(medicine.getReminderId());
          }
           return medicineDao.deleteMedicine(medicine.getId());
       }
        return null;
    }
}
