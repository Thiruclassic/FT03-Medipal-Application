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
import iss.medipal.dao.MedicineDao;
import iss.medipal.model.Medicine;

/**
 * Created by Naveen on 3/2/17.
 */

public class MedicineDaoImpl extends BaseDao implements MedicineDao{

    SimpleDateFormat dateFormat;

    public static MedicineDaoImpl newInstance(Context context) {
        return new MedicineDaoImpl(context);
    }

    public MedicineDaoImpl(Context context){
        super(context);
    }


    @Override
    public int addMedicine(Medicine medicine) {
        ContentValues values=new ContentValues();
        values.put(DBConstants.MEDICINE_NAME,medicine.getMedicine());
        values.put(DBConstants.MEDICINE_DESCRIPTION,medicine.getDescription());
        values.put(DBConstants.MEDICINE_CATID,medicine.getCatId());
        values.put(DBConstants.MEDICINE_REMINDER_ID,medicine.getReminderId());
        values.put(DBConstants.MEDICINE_REMIND,medicine.isRemind());
        values.put(DBConstants.MEDICINE_QUATITY,medicine.getQuantity());
        values.put(DBConstants.MEDICINE_DOSAGE,medicine.getDosage());
        values.put(DBConstants.MEDICINE_THRESHOLD,medicine.getThreshold());
        values.put(DBConstants.MEDICINE_DATE_ISSUED,String.valueOf(medicine.getDateIssued()));
        values.put(DBConstants.MEDICINE_EXPIRY_FACTOR,medicine.getExpireFactor());
        int id=(int)database.insert(DBConstants.TABLE_MEDICINE,null,values);
        Log.d("insert Medicine",String.valueOf(id));

        return id;
    }

    @Override
    public int updateMedicine(Medicine medicine) {
        ContentValues values=new ContentValues();
        values.put(DBConstants.MEDICINE_NAME,medicine.getMedicine());
        values.put(DBConstants.MEDICINE_DESCRIPTION,medicine.getDescription());
        values.put(DBConstants.MEDICINE_CATID,medicine.getCatId());
        values.put(DBConstants.MEDICINE_REMINDER_ID,medicine.getReminderId());
        values.put(DBConstants.MEDICINE_REMIND,medicine.isRemind());
        values.put(DBConstants.MEDICINE_QUATITY,medicine.getQuantity());
        values.put(DBConstants.MEDICINE_DOSAGE,medicine.getDosage());
        values.put(DBConstants.MEDICINE_THRESHOLD,medicine.getThreshold());
        values.put(DBConstants.MEDICINE_DATE_ISSUED,medicine.getDateIssued().toString());
        values.put(DBConstants.MEDICINE_EXPIRY_FACTOR,medicine.getExpireFactor());
        int id=(int)database.update(DBConstants.TABLE_MEDICINE,values,"id=?",new String[]{String.valueOf(medicine.getId())});
        Log.d("update Medicine",String.valueOf(id));
        return id;
    }

    @Override
    public int deleteMedicine(int medicineId) {
        return 0;
    }

    @Override
    public Medicine getMedicinebyId(int medicineId) {

        String query="Select * from "+ DBConstants.TABLE_MEDICINE+ " where id=?";
        String[] args=new String[1];
        args[0]=String.valueOf(medicineId);
        Medicine medicine=new Medicine();
        dateFormat =new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        try {
            Cursor cursor = database.rawQuery(query, args);

            if (cursor.moveToNext()) {
                medicine.setId(medicineId);
                medicine.setMedicine(cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_NAME)));
                medicine.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_DESCRIPTION)));
                medicine.setDosage(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_DOSAGE)));
                medicine.setQuantity(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_QUATITY)));
                medicine.setThreshold(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_THRESHOLD)));
                medicine.setRemind(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_REMIND))==1);
                medicine.setExpireFactor(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_EXPIRY_FACTOR)));
                medicine.setCatId(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_CATID)));
                String dateIssued = cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_DATE_ISSUED));
                cursor.getLong(cursor.getColumnIndex(DBConstants.MEDICINE_DATE_ISSUED));
                Log.e("isdate",dateIssued);
                medicine.setDateIssued(dateFormat.parse(dateIssued));
                medicine.setReminderId(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_REMINDER_ID)));

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.d("Error",e.getMessage());
        }
        return medicine;
    }

    @Override
    public Medicine getMedicinebyName(int medicineId) {
        return null;
    }

    @Override
    public List<String> getAllMedicinesName() {
        String query="Select * from "+ DBConstants.TABLE_MEDICINE;
        Cursor cursor=database.rawQuery(query,null);
        List<String> medicines=new ArrayList<>();

        while (cursor.moveToNext())
        {
            medicines.add(cursor.getString(1));
        }

        return medicines;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        String query="Select * from "+ DBConstants.TABLE_MEDICINE;
        Cursor cursor=database.rawQuery(query,null);
        List<Medicine> medicines=new ArrayList<>();
        dateFormat =new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        while (cursor.moveToNext())
        {
            try {
                Medicine medicine = new Medicine();
                medicine.setId(cursor.getInt(0));
                medicine.setMedicine(cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_NAME)));
                medicine.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_DESCRIPTION)));
                medicine.setCatId(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_CATID)));
                medicine.setReminderId(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_REMINDER_ID)));
                medicine.setRemind((cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_REMIND))==1));
                medicine.setQuantity(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_QUATITY)));
                medicine.setDosage(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_DOSAGE)));
                medicine.setDateIssued(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.MEDICINE_DATE_ISSUED))));
                medicine.setExpireFactor(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_EXPIRY_FACTOR)));
                medicine.setThreshold(cursor.getInt(cursor.getColumnIndex(DBConstants.MEDICINE_THRESHOLD)));
                medicines.add(medicine);
            }catch (Exception e) {
                Log.d("Error",e.getMessage());
            }
        }
        return medicines;
    }


}
