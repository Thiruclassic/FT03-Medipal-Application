package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iss.medipal.constants.DatabaseConstants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.helper.DatabaseHandler;
import iss.medipal.model.Medicine;
import iss.medipal.util.DatabaseUtility;

/**
 * Created by Naveen on 3/2/17.
 */

public class MedicineDaoImpl implements MedicineDao{

    DatabaseHandler dbHandler;

    public static MedicineDaoImpl newInstance() {

        MedicineDaoImpl impl=new MedicineDaoImpl();
        impl.dbHandler=DatabaseUtility.getDatabaseHandler();

        return impl;
    }


    @Override
    public int addMedicine(Medicine medicine) {


        SQLiteDatabase database=dbHandler.getWritableDatabase();

        ContentValues values=new ContentValues();

        Log.d("checkbox testing 2",String.valueOf(medicine.isRemind()));

        values.put("Medicine",medicine.getMedicine());
        values.put("Description",medicine.getDescription());
        values.put("CatId",medicine.getCatId());
        values.put("ReminderId",medicine.getReminderId());
        values.put("Remind",medicine.isRemind());
        values.put("Quantity",medicine.getQuantity());
        values.put("Dosage",medicine.getDosage());
        values.put("Threshold",medicine.getThreshold());
        values.put("DateIssued",medicine.getDateIssued().toString());
        values.put("ExpireFactor",medicine.getExpireFactor());


        int id=(int)database.insert(DatabaseConstants.TABLE_MEDICINE,null,values);

        return id;
    }

    @Override
    public int updateMedicine(Medicine medicine) {


        SQLiteDatabase database=dbHandler.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("Medicine",medicine.getMedicine());
        values.put("Description",medicine.getDescription());
        values.put("CatId",medicine.getCatId());
        values.put("ReminderId",medicine.getReminderId());
        values.put("Remind",medicine.isRemind());
        values.put("Quantity",medicine.getQuantity());
        values.put("Dosage",medicine.getDosage());
        values.put("Threshold",medicine.getThreshold());
        values.put("DateIssued",medicine.getDateIssued().toString());
        values.put("ExpireFactor",medicine.getExpireFactor());
        Log.d("data check",String.valueOf(medicine.getId()));
        int id=(int)database.update(DatabaseConstants.TABLE_MEDICINE,values,"id=?",new String[]{String.valueOf(medicine.getId())});
        return id;
    }

    @Override
    public int deleteMedicine(int medicineId) {
        return 0;
    }

    @Override
    public Medicine getMedicinebyId(int medicineId) {

        String query="Select * from "+ DatabaseConstants.TABLE_MEDICINE+ " where id=?";
        SQLiteDatabase database=dbHandler.getWritableDatabase();
        String[] args=new String[1];
        args[0]=String.valueOf(medicineId);

        Medicine medicine=new Medicine();
        try {
            Cursor cursor = database.rawQuery(query, args);

            if (cursor.moveToNext()) {

                medicine.setId(medicineId);
                medicine.setMedicine(cursor.getString(cursor.getColumnIndex("Medicine")));
                medicine.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                medicine.setDosage(cursor.getInt(cursor.getColumnIndex("Dosage")));
                medicine.setQuantity(cursor.getInt(cursor.getColumnIndex("Quantity")));
                medicine.setThreshold(cursor.getInt(cursor.getColumnIndex("Threshold")));
                medicine.setRemind(cursor.getInt(cursor.getColumnIndex("Remind"))==1);
                medicine.setExpireFactor(cursor.getInt(cursor.getColumnIndex("ExpireFactor")));
                medicine.setCatId(cursor.getInt(cursor.getColumnIndex("CatID")));
                String dateIssued = cursor.getString(cursor.getColumnIndex("DateIssued"));
                SimpleDateFormat dateFormat =new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                cursor.getLong(cursor.getColumnIndex("DateIssued"));
                medicine.setDateIssued(dateFormat.parse(dateIssued));

                medicine.setReminderId(cursor.getInt(cursor.getColumnIndex("ReminderID")));


            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
        return medicine;
    }

    @Override
    public Medicine getMedicinebyName(int medicineId) {
        return null;
    }

    @Override
    public List<String> getAllMedicines() {

        String query="Select * from "+ DatabaseConstants.TABLE_MEDICINE;

        SQLiteDatabase database=dbHandler.getReadableDatabase();

        Cursor cursor=database.rawQuery(query,null);
        List<String> medicines=new ArrayList<>();

        while (cursor.moveToNext())
        {
            medicines.add(cursor.getString(1));
        }

        return medicines;
    }
}
