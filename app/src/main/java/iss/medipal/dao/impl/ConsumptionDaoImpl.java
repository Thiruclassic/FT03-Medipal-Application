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
import iss.medipal.dao.MedicineDao;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;

/**
 * Created by junaidramis on 20/3/17.
 */

public class ConsumptionDaoImpl extends BaseDao implements ConsumptionDao {

    private SimpleDateFormat mDateFormat;
    private MedicineDao medicineDao;

    public static ConsumptionDaoImpl newInstance(Context context) {
        ConsumptionDaoImpl consumptionDao=new ConsumptionDaoImpl(context);
        consumptionDao.medicineDao=new MedicineDaoImpl(context);
        return consumptionDao;

    }

    public ConsumptionDaoImpl(Context context){
        super(context);
        mDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
    }

    @Override
    public long createConsumtion(Consumption consumption){
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.CONSUMPTION_MEDID, consumption.getMedicineId());
            values.put(DBConstants.CONSUMPTION_QUANTITY, consumption.getQuantity());
            values.put(DBConstants.CONSUMPTION_CONSUMED_ON, mDateFormat.format(consumption.getConsumedOn()));
            long id = (int) database.insert(DBConstants.TABLE_CONSUMPTION, null, values);
            return id;
        } catch (NullPointerException e){
            return -1;
        }
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
    public void deleteConsumtion(Consumption consumption){
        try {
            database.delete(DBConstants.TABLE_CONSUMPTION, DBConstants.CONSUMPTION_MEDID +"=? and "+
                    DBConstants.CONSUMPTION_CONSUMED_ON+"=?", new String[]{String.valueOf(consumption.getMedicineId()),
                    mDateFormat.format(consumption.getConsumedOn())});
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteConsumtionByMedId(int medId) {
        try {
            database.delete(DBConstants.TABLE_CONSUMPTION, DBConstants.CONSUMPTION_MEDID +"=? ",
                    new String[]{String.valueOf(medId)});
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void clearTable() {
        database.delete(DBConstants.TABLE_CONSUMPTION, null, null);
    }

    @Override
    public void close() {

    }
}
