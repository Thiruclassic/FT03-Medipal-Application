package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.MediPalApplication;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.model.PersonalBio;

/**
 * Created by junaidramis on 25/3/17.
 */

public class AddHealthBioTask extends AsyncTask<HealthBio, Void, Long> {
    private HealthBioDao mHealthDao;

    public AddHealthBioTask(Context context) {
        this.mHealthDao = new HealthBioDaoImpl(context);
    }

    @Override
    protected Long doInBackground(HealthBio... params) {
        int result = mHealthDao.createHealthBio(params[0]);
        for(HealthBio bio : MediPalApplication.getPersonStore().getmPersonalBio().getHealthBios()){
            if(bio.equals(params[0])){
                bio.setId(result);
            }
        }
        return (long)result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mHealthDao != null)
                mHealthDao.close();
    }
}