package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by junaidramis on 25/3/17.
 */

public class DeleteHealthBioTask extends AsyncTask<HealthBio,Void,Long> {

    private HealthBioDao healthDao;

    public DeleteHealthBioTask(Context context)
    {
        healthDao=new HealthBioDaoImpl(context);
    }
    @Override
    protected Long doInBackground(HealthBio... params) {
        long id = healthDao.deleteHealthBio(params[0].getId());
        return id;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (healthDao != null)
                healthDao.close();
    }
}
