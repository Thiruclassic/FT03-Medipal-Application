package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;

/**
 * Created by Sreekumar on 3/12/2017.
 */

public class AddAppointmentTask  extends AsyncTask<Appointment, Void, Long> {

    private AppointmentDaoImpl mAppointmentDao;

    public AddAppointmentTask(Context context){

        this.mAppointmentDao =new AppointmentDaoImpl(context);

    }
    @Override
    protected Long doInBackground(Appointment... params) {
//        long result = mAppointmentDao.createPersonalBio(params[0]);
       long  result=1L;
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mAppointmentDao != null)
                mAppointmentDao.close();
    }

}
