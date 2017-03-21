package iss.medipal.asyncs;


import android.content.Context;
import android.os.AsyncTask;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.model.Consumption;

/**
 * Created by junaidramis on 20/3/17.
 */

public class AddConsumptionTask extends AsyncTask<Consumption, Void, Long> {

    private Context mContext;
    private ConsumptionDao mConsumptionDao;

    public AddConsumptionTask(Context context) {
        this.mContext = context;
        this.mConsumptionDao = new ConsumptionDaoImpl(context);
    }

    @Override
    protected Long doInBackground(Consumption... params) {
        long reminderId = mConsumptionDao.createConsumtion(params[0]);
        return reminderId;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mConsumptionDao != null)
                mConsumptionDao.close();
    }
}