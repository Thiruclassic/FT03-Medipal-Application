package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class DeleteAppointmentTask extends AsyncTask<Appointment, Void, Long> {

    private Context mContext;
    private AppointmentDaoImpl mAppointmentDao;

    public DeleteAppointmentTask(Context context) {
        this.mContext = context;
        this.mAppointmentDao = new AppointmentDaoImpl(context);

    }
    @Override
    protected Long doInBackground(Appointment... params) {
        long result;
        Boolean status = mAppointmentDao.deleteAppointment(params[0]);
        if (status) {
            result = 1L;
        } else {

            result = -1L;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
            if (mAppointmentDao != null)
                mAppointmentDao.close();
    }
}
