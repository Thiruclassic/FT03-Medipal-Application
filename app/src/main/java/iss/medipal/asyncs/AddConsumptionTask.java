package iss.medipal.asyncs;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import iss.medipal.MediPalApplication;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;
import iss.medipal.receivers.AlarmReceiver;

/**
 * Created by junaidramis on 20/3/17.
 */

public class AddConsumptionTask extends AsyncTask<Consumption, Void, Long> {

    private Context mContext;
    private ConsumptionDao mConsumptionDao;
    private MedicineDao mMedicineDao;

    public AddConsumptionTask(Context context) {
        this.mContext = context;
        this.mConsumptionDao = new ConsumptionDaoImpl(context);
        this.mMedicineDao=new MedicineDaoImpl(context);
    }

    @Override
    protected Long doInBackground(Consumption... params) {
        mConsumptionDao.createConsumtion(params[0]);
        Medicine medicine = mMedicineDao.getMedicinebyId(params[0].getMedicineId());
        if(medicine != null) {
            long reminderId = medicine.getReminderId();
            updateMedicineTotalQuantity(medicine);
            return reminderId;
        }
        return (long)1;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mConsumptionDao != null)
                mConsumptionDao.close();
    }

    public void updateMedicineTotalQuantity(Medicine medicine)
    {
        medicine.setQuantity(medicine.getQuantity()-medicine.getDosage());
        for(Medicine medicine1: MediPalApplication.getPersonStore().getmPersonalBio().getMedicines()){
            if(medicine.equals(medicine1)){
                medicine1.setQuantity(medicine.getQuantity());
            }
        }
        if(medicine.getQuantity() < medicine.getThreshold())
        {

        }
        mMedicineDao.updateMedicineDosage(medicine);
    }

    public void sendRefillNotifier(Medicine medicine)
    {
        try {
            Intent intent = new Intent(mContext, AlarmReceiver.class);
            intent.putExtra(DBConstants.MEDICINE_NAME, medicine.getMedicine());
            intent.putExtra(DBConstants.TABLE_REMINDER, Boolean.TRUE);
            intent.putExtra(DBConstants.REMINDER_ID, medicine.getReminderId());
            intent.putExtra(DBConstants.APP_ID, medicine.getId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, Constants.REFILL_BROADCAST_ID + medicine.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.send();
        }
        catch(Exception e)
        {
            Log.d("Error","Exception in sending Refill Reminder");
        }

    }
}