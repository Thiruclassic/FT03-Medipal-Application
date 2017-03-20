package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.MediPalApplication;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.model.PersonalBio;

/**
 * Created by Mridul on 20-03-2017.
 */

public class AddHealthBioTask extends AsyncTask<HealthBio, Void, Long> {
    private HealthBioDaoImpl mHealthBioDao;

    public AddHealthBioTask(Context context){

        this.mHealthBioDao =new HealthBioDaoImpl(context);

    }
    @Override
    protected Long doInBackground(HealthBio... params) {
        long  result=1L;
        PersonalBio personalBio=MediPalApplication.getPersonStore().getmPersonalBio();
        personalBio.setHealthBios(mHealthBioDao.getAllHealthBio());

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mHealthBioDao != null)
                mHealthBioDao.close();
    }

}
