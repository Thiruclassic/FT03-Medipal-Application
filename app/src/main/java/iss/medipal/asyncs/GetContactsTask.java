package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Thirumal on 24/3/2017.
 */

public class GetContactsTask extends AsyncTask <Void,Void,List<InCaseofEmergencyContact>> {

    private ICEDao iceDao;
    public GetContactsTask(Context context){
        iceDao=new IceDaoImpl(context);
    }

    @Override
    protected List<InCaseofEmergencyContact> doInBackground(Void... params) {

        return iceDao.getAllContacts();
    }

    @Override
    protected void onPostExecute(List<InCaseofEmergencyContact> contacts) {
        MediPalApplication.getPersonStore().getmPersonalBio().setContacts(contacts);
        iceDao.close();
    }
}
