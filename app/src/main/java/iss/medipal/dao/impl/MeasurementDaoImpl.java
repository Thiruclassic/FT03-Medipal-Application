package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.model.Measurement;

/**
 * Created by sreekumar on 3/18/2017.
 */

public class MeasurementDaoImpl extends BaseDao implements MeasurementDao{


    SimpleDateFormat dateFormat;
    public MeasurementDaoImpl(Context context) {
        super(context);
    }

    public static MeasurementDaoImpl newInstance(Context context) {
        return new MeasurementDaoImpl(context);
    }

    @Override
    public Measurement getMeasurement(){

        return null;

    }
    @Override
    public Measurement getMeasurement(int id){

        return null;
    }
    @Override
    public List<Measurement> getMeasurements(){

        List<Measurement> mesList = new ArrayList<>();

        return mesList;
    }
    public int addBloodPressure(Measurement measurement){

        ContentValues values = new ContentValues();
        values.put(DBConstants.MES_SYS, measurement.getSystolic());
        values.put(DBConstants.MES_DIA, measurement.getDiastolic());
        values.put(DBConstants.MES_measuredOn, measurement.getMeasuredOn().toString());
        int id = (int) database.insert(DBConstants.TABLE_MEASUREMENT, null, values);
        return id;
    }

    @Override
    public List<Measurement> getBloodPressureValues(){

        String query = "Select * from " + DBConstants.TABLE_MEASUREMENT;
        Cursor cursor = database.rawQuery(query, null);
        List<Measurement> bloodPressureList = new ArrayList<>();
        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        while (cursor.moveToNext()) {
            try {

                Measurement measurement = new Measurement();
                measurement.setId(cursor.getColumnIndex(DBConstants.MES_ID));
                measurement.setDiastolic(cursor.getColumnIndex(DBConstants.MES_DIA));
                measurement.setSystolic(cursor.getColumnIndex(DBConstants.MES_SYS));
                measurement.setMeasuredOn(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));
                bloodPressureList.add(measurement);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }

        return bloodPressureList;

    }

}