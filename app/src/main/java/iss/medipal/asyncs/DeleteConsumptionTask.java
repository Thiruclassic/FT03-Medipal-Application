package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.model.Consumption;

/**
 * Created by junaidramis on 21/3/17.
 */

public class DeleteConsumptionTask extends AsyncTask<Consumption, Void, Void> {

    private Context mContext;
    private ConsumptionDao mConsumptionDao;

    public DeleteConsumptionTask(Context context) {
        this.mContext = context;
        this.mConsumptionDao = new ConsumptionDaoImpl(context);
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
}