package iss.medipal.dao;

import java.util.ArrayList;

import iss.medipal.model.Appointment;


/**
 * Created by Sreekumar on 2/24/17.
 */

public interface AppointmentDao {

    public int addAppointment(Appointment appointment);

    public int updateAppointment(Appointment appointment);

    public Boolean deleteAppointment(Appointment appointment);

    public ArrayList<Appointment> getAllAppointments();

    public void clearTable();

    public void close();

}
