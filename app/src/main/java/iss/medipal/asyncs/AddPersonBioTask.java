package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.PersonalBio;

/**
 * Created by junaidramis on 10/3/17.
 */

public class AddPersonBioTask extends AsyncTask<PersonalBio, Void, Long> {
    private PersonBioDaoImpl mBioDao;

    public AddPersonBioTask(Context context) {
        this.mBioDao = new PersonBioDaoImpl(context);
    }

    @Override
    protected Long doInBackground(PersonalBio... params) {
        long result = mBioDao.createPersonalBio(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mBioDao != null)
                mBioDao.close();
    }


}