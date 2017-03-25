package iss.medipal.dao;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.model.Appointment;
import iss.medipal.model.Medicine;


/**
 * Created by Sreekumar on 2/24/17.
 */

public interface AppointmentDao {

    public int addAppointment(Appointment appointment);
    public int modifyAppointment(Appointment appointment);
    public int updateAppointment(Appointment appointment);
    public Boolean deleteAppointment(int appointmentId);

    public Appointment getAppointmentById(int appointmentId);
    public ArrayList<Appointment> getAllAppointments();
    public void close();

}
