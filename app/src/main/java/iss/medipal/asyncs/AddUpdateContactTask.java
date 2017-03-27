package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 19/3/2017.
 */

public class AddUpdateContactTask extends AsyncTask<InCaseofEmergencyContact, Void, Integer> {
    private Context mContext;
    private ICEDao mIceDao;
    boolean isEdit;
    private InCaseofEmergencyContact contact;

    public AddUpdateContactTask(Context context,boolean isEdit)
    {
        this.mContext = context;
        this.mIceDao = new IceDaoImpl(context);
        this.isEdit=isEdit;
    }

    @Override
    protected Integer doInBackground(InCaseofEmergencyContact... params) {
        Integer result;
        contact=params[0];
        if(isEdit) {
            result = mIceDao.updateContact(params[0]);
        }
        else
        {
            result = mIceDao.addContact(params[0]);
            contact.setId(result);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != -1)
            if (mIceDao != null)
                mIceDao.close();

        if(!isEdit) {
            List<InCaseofEmergencyContact> contacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();
            for (int i = contacts.size() - 1; i >= 0; i--) {
                if (contact.equals(contacts.get(i))) {
                    contacts.set(i, contact);
                    break;
                }
            }
        }
    }
}
