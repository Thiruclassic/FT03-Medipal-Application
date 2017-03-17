package iss.medipal.model;

import iss.medipal.asyncs.AddAppointmentTask;
import iss.medipal.dao.AppointmentDao;
import android.content.Context;

/**
 * Created by sreekumar on 3/12/2017.
 */

public class AppointmentStore {


    private Appointment mAppointment;
    private AppointmentDao mAppointmentDao;
    private Context mContext;

    //Tasks
    private AddAppointmentTask addAppointmentTask;

    public AppointmentStore(Appointment mAppointment, Context context){
        this.mAppointment = mAppointment;
        this.mContext = context;
    }

    public Appointment getmAppointment() {
        return mAppointment;
    }

    public void setmAppointment(Appointment mAppointment) {
        this.mAppointment = mAppointment;
    }
    public void addAppointment(Appointment appointment){

        mAppointment.setDescription(appointment.getDescription());
        mAppointment.setLocation(appointment.getLocation());
        mAppointment.setAppointment(appointment.getAppointment());
        mAppointment.setId(appointment.getId());
        addAppointmentTask = new AddAppointmentTask(mContext);
        addAppointmentTask.execute(appointment);
    }

}
