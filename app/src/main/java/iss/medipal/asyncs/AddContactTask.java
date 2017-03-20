package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;

import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 19/3/2017.
 */

public class AddContactTask extends AsyncTask<InCaseofEmergencyContact, Void, Long> {
    private Context mContext;
    private ICEDao mIceDao;

    public AddContactTask(Context context)
    {
        this.mContext = context;
        this.mIceDao = new IceDaoImpl(context);
    }

    @Override
    protected Long doInBackground(InCaseofEmergencyContact... params) {
        long result = mIceDao.addContact(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mIceDao != null)
                mIceDao.close();
    }
}
