package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Thirumal on 24/3/2017.
 */

public class DeleteContactTask extends AsyncTask <InCaseofEmergencyContact,Void,Long>{

    private ICEDao iceDao;

    public DeleteContactTask(Context context)
    {
     iceDao=new IceDaoImpl(context);
    }
    @Override
    protected Long doInBackground(InCaseofEmergencyContact... params) {
        long id=iceDao.deleteContact(params[0].getId());
        return id;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (iceDao != null)
                iceDao.close();
    }
}
