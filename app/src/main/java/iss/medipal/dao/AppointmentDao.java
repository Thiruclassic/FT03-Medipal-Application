package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Appointment;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface AppointmentDao {
    public int addAppointment(Appointment appointment);
    public int modifyAppointment(Appointment appointment);
    public int deleteAppointment(int appointmentId);

    public Appointment getAppointmentById(int appointmentId);
    public List<Appointment> getAllAppointments();

}
