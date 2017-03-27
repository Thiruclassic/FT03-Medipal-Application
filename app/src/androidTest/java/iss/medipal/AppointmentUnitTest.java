package iss.medipal;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;

/**
 * Created by sreek on 3/26/2017.
 */

public class AppointmentUnitTest extends TestCase implements MedipalUnitTest {

    Appointment appointment;
    AppointmentDao appointmentDao;
    List<Appointment> appointmentList;


    @BeforeClass
    public void setUp() {
        appointment = new Appointment();
        appointmentDao = new AppointmentDaoImpl(context);
//        appointmentList = appointmentDao.getAllAppointments();
    }

    @Test
    public void deleteAppointmentTest() throws Exception {

//        Boolean status = appointmentDao.deleteAppointment(appointmentList.lastIndexOf(appointment));
//        assertTrue(status);
    }

    @Test
    public void addAppointmentTest() throws Exception {


    }

    @Test
    public void modifyAppointmentTest() {


    }
    @Test
    public void updateAppointmentTest(){


    }
    @Test
    public void  getAllAppointmentsTest(){


    }


}
