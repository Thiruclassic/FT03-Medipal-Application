package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;

/**
 * Created by junaidramis on 20/3/17.
 */

public class ConsumptionDaoImpl extends BaseDao implements ConsumptionDao {

    private SimpleDateFormat mDateFormat;

    public static ConsumptionDaoImpl newInstance(Context context) {
        return new ConsumptionDaoImpl(context);
    }

    public ConsumptionDaoImpl(Context context){
        super(context);
        mDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    }

    @Override
    public long createConsumtion(Consumption consumption){
        ContentValues values=new ContentValues();
        values.put(DBConstants.CONSUMPTION_MEDID,consumption.getMedicineId());
        values.put(DBConstants.CONSUMPTION_QUANTITY,consumption.getQuantity());
        values.put(DBConstants.CONSUMPTION_CONSUMED_ON,mDateFormat.format(consumption.getConsumedOn()));
        long id=(int)database.insert(DBConstants.TABLE_CONSUMPTION,null,values);
        return id;
    }


    @Override
    public Consumption getConsumptionForMedicine(int medicineId) {
        return null;
    }

    @Override
    public List<Consumption> getAllConsumptions() {
        String query = "Select * from "+ DBConstants.TABLE_CONSUMPTION;
        Cursor cursor = database.rawQuery(query,null);
        List<Consumption> consumptions=new ArrayList<>();
        while (cursor.moveToNext())
        {
            try {
                Consumption consumption = new Consumption();
                consumption.setId(cursor.getInt(0));
                consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBConstants.CONSUMPTION_MEDID)));
                consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBConstants.CONSUMPTION_QUANTITY)));
                consumption.setConsumedOn(mDateFormat.parse(
                        cursor.getString(cursor.getColumnIndex(DBConstants.CONSUMPTION_CONSUMED_ON))));
                consumptions.add(consumption);
            }catch (Exception e) {
                Log.d("Error",e.getMessage());
            }
        }
        return consumptions;
    }

    @Override
    public void deleteConsumtion(Consumption consumtion){
        try {
            database.delete(DBConstants.TABLE_CONSUMPTION, DBConstants.CONSUMPTION_MEDID +"=? and "+
                    DBConstants.CONSUMPTION_CONSUMED_ON+"=?", new String[]{String.valueOf(consumtion.getMedicineId()),
                    mDateFormat.format(consumtion.getConsumedOn())});
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Consumption> getAllConsumptionsForLastXDays(int days) {
        return null;
    }

    @Override
    public List<Consumption> getMissedConsumptions() {
        return null;
    }

    @Override
    public void close() {

    }
}
