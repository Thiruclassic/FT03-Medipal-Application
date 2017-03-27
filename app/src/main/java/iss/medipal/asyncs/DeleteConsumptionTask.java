package iss.medipal.asyncs;

import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.MediPalApplication;
import iss.medipal.constants.Constants;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;

/**
 * Created by junaidramis on 21/3/17.
 */

public class DeleteConsumptionTask extends AsyncTask<Consumption, Void, Void> {

    private Context mContext;
    private ConsumptionDao mConsumptionDao;
    private MedicineDao mMedicineDao;

    public DeleteConsumptionTask(Context context) {
        this.mContext = context;
        this.mConsumptionDao = new ConsumptionDaoImpl(context);
        this.mMedicineDao=new MedicineDaoImpl(context);
    }

    @Override
    protected Void doInBackground(Consumption... params) {
        mConsumptionDao.deleteConsumtion(params[0]);
        Medicine medicine = mMedicineDao.getMedicinebyId(params[0].getMedicineId());
        if(medicine != null) {
            updateMedicineTotalQuantity(medicine);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void a) {
            if (mConsumptionDao != null)
                mConsumptionDao.close();
    }

    private void updateMedicineTotalQuantity(Medicine medicine)
    {
        medicine.setQuantity(medicine.getQuantity() + medicine.getDosage());
        for(Medicine medicine1: MediPalApplication.getPersonStore().getmPersonalBio().getMedicines()){
            if(medicine.equals(medicine1)){
                medicine1.setQuantity(medicine.getQuantity());
            }
        }
        mMedicineDao.updateMedicineDosage(medicine);
    }
}