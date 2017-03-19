package iss.medipal.dao.impl;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.model.Appointment;
import iss.medipal.model.Medicine;

/**
 * Created by sreekumar on 3/12/2017.
 */

public class AppointmentDaoImpl extends BaseDao implements AppointmentDao {

    SimpleDateFormat dateFormat;

    public static AppointmentDaoImpl newInstance(Context context) {
        return new AppointmentDaoImpl(context);
    }

    public AppointmentDaoImpl(Context context) {
        super(context);
    }

    @Override
    public int addAppointment(Appointment appointment) {

        ContentValues values = new ContentValues();
        values.put(DBConstants.APP_LOCATION, appointment.getLocation());
        values.put(DBConstants.APP_DATETIME, appointment.getAppointment().toString());
        values.put(DBConstants.APP_DESCRIPTION, appointment.getDescription());
        int id = (int) database.insert(DBConstants.TABLE_APPOINTMENT, null, values);
        return id;
    }

    @Override
    public int modifyAppointment(Appointment appointment) {
        return 0;
    }

    @Override
    public int deleteAppointment(int appointmentId) {
        return 0;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return null;
    }


    public List<String> getAllAppointmentName() {
        String query = "Select * from " + DBConstants.TABLE_APPOINTMENT;
        Cursor cursor = database.rawQuery(query, null);
        List<String> appointmentList = new ArrayList<>();

        while (cursor.moveToNext()) {
            appointmentList.add(cursor.getString(1));
        }

        return appointmentList;
    }

    @Override
    public List<Appointment> getAllAppointments() {

        String query = "Select * from " + DBConstants.TABLE_APPOINTMENT;
        Cursor cursor = database.rawQuery(query, null);
        List<Appointment> appointments = new ArrayList<>();

        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        while (cursor.moveToNext()) {
            try {

                Appointment appointment = new Appointment();
                appointment.setId(cursor.getColumnIndex(DBConstants.APP_ID));
                appointment.setAppointment(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.APP_DATETIME))));
                appointment.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.APP_DESCRIPTION)));
                appointment.setLocation(cursor.getString(cursor.getColumnIndex(DBConstants.APP_LOCATION)));

                appointments.add(appointment);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
        return appointments;
    }

}
