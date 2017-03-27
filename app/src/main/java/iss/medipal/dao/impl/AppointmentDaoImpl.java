package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.model.Appointment;

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
    public int updateAppointment(Appointment appointment) {
        int id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.APP_LOCATION, appointment.getLocation());
            values.put(DBConstants.APP_DATETIME, appointment.getAppointment().toString());
            values.put(DBConstants.APP_DESCRIPTION, appointment.getDescription());
            id = (int) database.update(DBConstants.TABLE_APPOINTMENT, values, "id=?", new String[]{String.valueOf(appointment.getId())});
        } catch (NullPointerException exception) {
            Log.d("Invalid data", exception.getMessage());
        }
        return id;
    }

    @Override
    public Boolean deleteAppointment(Appointment appointment) {
        Boolean status = false;
        try {
            status = database.delete(DBConstants.TABLE_APPOINTMENT, "ID" + "=" + " '" + appointment.getId() + "'", null) > 0;
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
        return status;
    }

    @Override
    public ArrayList<Appointment> getAllAppointments() {

        String query = "Select * from " + DBConstants.TABLE_APPOINTMENT;
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        while (cursor.moveToNext()) {
            try {

                Appointment appointment = new Appointment();
                appointment.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.APP_ID)));
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
