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
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.model.Pulse;
import iss.medipal.model.Temperature;
import iss.medipal.model.Weight;

/**
 * Created by sreekumar on 3/18/2017.
 */

public class MeasurementDaoImpl extends BaseDao implements MeasurementDao {


    SimpleDateFormat dateFormat;

    public MeasurementDaoImpl(Context context) {
        super(context);
    }

    public static MeasurementDaoImpl newInstance(Context context) {
        return new MeasurementDaoImpl(context);
    }

    @Override
    public Measurement getMeasurement() {

        return null;

    }

    @Override
    public Measurement getMeasurement(int id) {

        return null;
    }

    @Override
    public List<Measurement> getMeasurements() {

        List<Measurement> mesList = new ArrayList<>();

        return mesList;
    }


    @Override
    public List<BloodPressure> getBloodPressureValues() {
        BloodPressure bloodPressure = null;
        String query = "Select * from " + DBConstants.TABLE_MEASUREMENT + " where Systolic IS NOT NULL";
        Cursor cursor = database.rawQuery(query, null);
        List<BloodPressure> bloodPressureList = new ArrayList<>();
        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        while (cursor.moveToNext()) {
            try {

                bloodPressure = new BloodPressure(cursor.getInt(cursor.getColumnIndex(DBConstants.MES_ID)),
                        cursor.getInt(cursor.getColumnIndex(DBConstants.MES_SYS)),
                        cursor.getInt(cursor.getColumnIndex(DBConstants.MES_DIA)),
                        dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));
               /* measurement.setId(cursor.getColumnIndex(DBConstants.MES_ID));
                measurement.setDiastolic(cursor.getColumnIndex(DBConstants.MES_DIA));
                measurement.setSystolic(cursor.getColumnIndex(DBConstants.MES_SYS));
                measurement.setMeasuredOn(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));*/
                bloodPressureList.add(bloodPressure);

            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }

        return bloodPressureList;

    }

    public List<Weight> getWeightValues() {
        Weight weight = null;
        String query = "Select * from " + DBConstants.TABLE_MEASUREMENT + " where Weight IS NOT NULL";
        Cursor cursor = database.rawQuery(query, null);
        List<Weight> weightList = new ArrayList<>();
        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);

        while (cursor.moveToNext()) {
            try {
                weight = new Weight(cursor.getInt(cursor.getColumnIndex(DBConstants.MES_ID)),
                        cursor.getInt(cursor.getColumnIndex(DBConstants.MES_weight)),
                        dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));

                weightList.add(weight);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
        return weightList;

    }

    public Boolean deleteMeasurement(int measurementId) {
        Boolean status = false;
        try {
            status = database.delete(DBConstants.TABLE_MEASUREMENT, "ID" + "=" + " '" + measurementId + "'", null) > 0;

        } catch (Exception ex) {

            Log.d("Error", ex.getMessage());

        }
        return status;
    }


    public List<Temperature> getTempValues() {

        List<Temperature> temperatureList = new ArrayList<>();
        String query = "Select * from " + DBConstants.TABLE_MEASUREMENT + " where Temperature IS NOT NULL";
        Cursor cursor = database.rawQuery(query, null);
        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        Temperature temp = null;

        while (cursor.moveToNext()) {
            try {
                temp = new Temperature(cursor.getInt(cursor.getColumnIndex(DBConstants.MES_ID)),
                        cursor.getInt(cursor.getColumnIndex(DBConstants.MES_temp)),
                        dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));

                temperatureList.add(temp);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }

        return temperatureList;


    }

    public List<Pulse> getPulseValues() {

        List<Pulse> pulseList = new ArrayList<>();
        String query = "Select * from " + DBConstants.TABLE_MEASUREMENT + " where Pulse IS NOT NULL";
        ;
        Cursor cursor = database.rawQuery(query, null);
        dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        Pulse pulse = null;

        while (cursor.moveToNext()) {
            try {
                pulse = new Pulse(cursor.getInt(cursor.getColumnIndex(DBConstants.MES_ID)),
                        cursor.getInt(cursor.getColumnIndex(DBConstants.MES_PULSE)),
                        dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MES_measuredOn))));

                pulseList.add(pulse);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }

        return pulseList;


    }

    public int addBloodPressure(BloodPressure bloodPressure) {

        ContentValues values = new ContentValues();
        values.put(DBConstants.MES_SYS, bloodPressure.getSystolic());
        values.put(DBConstants.MES_DIA, bloodPressure.getDiastolic());
        values.put(DBConstants.MES_measuredOn, bloodPressure.getMeasuredOn().toString());
        int id = (int) database.insert(DBConstants.TABLE_MEASUREMENT, null, values);
        return id;
    }

    public int addWeight(Weight weight) {

        ContentValues values = new ContentValues();
        values.put(DBConstants.MES_weight, weight.getWeight());
        values.put(DBConstants.MES_measuredOn, weight.getMeasuredOn().toString());
        int id = (int) database.insert(DBConstants.TABLE_MEASUREMENT, null, values);
        return id;


    }

    public int addTemperature(Temperature temperature) {

        ContentValues values = new ContentValues();
        values.put(DBConstants.MES_temp, temperature.getTemperature());
        values.put(DBConstants.MES_measuredOn, temperature.getMeasuredOn().toString());
        int id = (int) database.insert(DBConstants.TABLE_MEASUREMENT, null, values);
        return id;


    }

    public int addPulse(Pulse pulse) {

        ContentValues values = new ContentValues();
        values.put(DBConstants.MES_PULSE, pulse.getPulse());
        values.put(DBConstants.MES_measuredOn, pulse.getMeasuredOn().toString());
        int id = (int) database.insert(DBConstants.TABLE_MEASUREMENT, null, values);
        return id;


    }

}