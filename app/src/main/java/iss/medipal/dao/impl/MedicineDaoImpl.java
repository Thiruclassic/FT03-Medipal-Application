package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.model.Medicine;

import static android.R.attr.id;

/**
 * Created by Thirumal on 3/2/17.
 */

public class MedicineDaoImpl extends BaseDao implements MedicineDao{

    SimpleDateFormat dateFormat;

    public static MedicineDaoImpl newInstance(Context context) {
        return new MedicineDaoImpl(context);
    }

    public MedicineDaoImpl(Context context){
        super(context);
        dateFormat = new SimpleDateFormat(Constants.ISSUE_DATE_FORMAT);
    }

    public MedicineDaoImpl()
    {
        //Default Constructor
    }


    @Override
    public int addMedicine(Medicine medicine) {
        int id=0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.MEDICINE_NAME, medicine.getMedicine());
            values.put(DBConstants.MEDICINE_DESCRIPTION, medicine.getDescription());
            values.put(DBConstants.MEDICINE_CATID, medicine.getCatId());
            values.put(DBConstants.MEDICINE_REMINDER_ID, medicine.getReminderId());
            values.put(DBConstants.MEDICINE_REMIND, medicine.isRemind());
            values.put(DBConstants.MEDICINE_QUATITY, medicine.getQuantity());
            values.put(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
            values.put(DBConstants.MEDICINE_THRESHOLD, medicine.getThreshold());
            values.put(DBConstants.MEDICINE_DATE_ISSUED, dateFormat.format(medicine.getDateIssued()));
            values.put(DBConstants.MEDICINE_EXPIRY_FACTOR, medicine.getExpireFactor());
            id = (int) database.insert(DBConstants.TABLE_MEDICINE, null, values);
            Log.d("insert Medicine", String.valueOf(id));
        }
        catch(NullPointerException exception)
        {
            Log.d("Invalid data",exception.getMessage());
        }

        return id;
    }

    @Override
    public int updateMedicine(Medicine medicine) {
        int id=0;
        try {
            ContentValues values = new ContentValues();
            values.put(DBConstants.MEDICINE_NAME, medicine.getMedicine());
            values.put(DBConstants.MEDICINE_DESCRIPTION, medicine.getDescription());
            values.put(DBConstants.MEDICINE_CATID, medicine.getCatId());
            values.put(DBConstants.MEDICINE_REMINDER_ID, medicine.getReminderId());
            values.put(DBConstants.MEDICINE_REMIND, medicine.isRemind());
            values.put(DBConstants.MEDICINE_QUATITY, medicine.getQuantity());
            values.put(DBConstants.MEDICINE_DOSAGE, medicine.getDosage());
            values.put(DBConstants.MEDICINE_THRESHOLD, medicine.getThreshold());
            values.put(DBConstants.MEDICINE_DATE_ISSUED, dateFormat.format(medicine.getDateIssued()));
            values.put(DBConstants.MEDICINE_EXPIRY_FACTOR, medicine.getExpireFactor());
            id=(int)database.update(DBConstants.TABLE_MEDICINE,values,"id=?",new String[]{String.valueOf(medicine.getId())});
        }
        catch (NullPointerException exception)
        {
            Log.d("Invalid data",exception.getMessage());
        }

        return id;
    }

    @Override
    public int updateMedicineDosage(Medicine medicine) {
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.MEDICINE_QUATITY, medicine.getQuantity());
        int id = database.update(DBConstants.TABLE_MEDICINE, cv, DBConstants.MEDICINE_ID + "= ?",
                new String[] {String.valueOf(medicine.getId())});
        return id;
    }

    @Override
    public int deleteMedicine(int medicineId) {

        int rowId = database.delete(DBConstants.TABLE_MEDICINE, "ID = ?",new String[]{String.valueOf(medicineId)});
        return rowId;
    }

    @Override
    public Medicine getMedicinebyId(int medicineId) {

        String query="Select * from "+ DBConstants.TABLE_MEDICINE+ " where id=?";
        String[] args=new String[1];
        args[0]=String.valueOf(medicineId);
        Medicine medicine=new Medicine();
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
