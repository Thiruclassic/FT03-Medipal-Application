package iss.medipal.asyncs;

import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;

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
        return null;
    }

    @Override
    protected void onPostExecute(Void a) {
            if (mConsumptionDao != null)
                mConsumptionDao.close();
    }

    public int updateMedicineTotalQuantity(Medicine medicine)
    {
        return mMedicineDao.updateMedicine(medicine);
    }
}