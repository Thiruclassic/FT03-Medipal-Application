package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.PersonalBio;
import iss.medipal.ui.interfaces.OnTaskCompleted;

/**
 * Created by junaidramis on 10/3/17.
 */

public class AddPersonBioTask extends AsyncTask<PersonalBio, Void, Long> {
    private PersonBioDaoImpl mBioDao;
    private OnTaskCompleted mCallback;

    public AddPersonBioTask(Context context, OnTaskCompleted callback) {
        this.mBioDao = new PersonBioDaoImpl(context);
        this.mCallback = callback;
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
        if(mCallback != null){
            mCallback.onTaskCompleted();
        }
    }
}