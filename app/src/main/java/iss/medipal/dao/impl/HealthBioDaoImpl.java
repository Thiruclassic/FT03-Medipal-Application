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
import iss.medipal.dao.HealthBioDao;
import iss.medipal.model.HealthBio;

/**
 * Created by junaidramis on 11/3/17.
 */

public class HealthBioDaoImpl extends BaseDao implements HealthBioDao {

    public static HealthBioDaoImpl newInstance(Context context) {
        return new HealthBioDaoImpl(context);
    }


    public HealthBioDaoImpl(Context context) {
        super(context);
    }
    // Insert Health Bio Data
    @Override
    public int createHealthBio(HealthBio healthBio) {
        ContentValues values=new ContentValues();
        values.put(DBConstants.HEALTH_CONDITION,healthBio.getCondition());
        values.put(DBConstants.HEALTH_START_DATE,healthBio.getStartDate().toString());
        values.put(DBConstants.HEALTH_CONDITION_TYPE,healthBio.getConditionType());
        int id=(int)database.insert(DBConstants.TABLE_HEALTH_BIO,null,values);
        return id;
    }

    @Override
    public int updateHealthBio(HealthBio bioData) {
        return 0;
    }

    @Override
    public int deleteHealthBio(int id) {
        int rowId = database.delete(DBConstants.TABLE_HEALTH_BIO, "ID = ?",new String[]{String.valueOf(id)});
        return rowId;

    }

    @Override
    public List<HealthBio> getAllHealthBio() {

        String query = "Select * from " + DBConstants.TABLE_HEALTH_BIO;
        Cursor cursor = database.rawQuery(query, null);

        List<HealthBio> healthbio_list = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        while (cursor.moveToNext()) {
            try {

                HealthBio healthBio = new HealthBio();
                healthBio.setId(cursor.getColumnIndex(DBConstants.APP_ID));
                healthBio.setStartDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex(DBConstants.HEALTH_START_DATE))).toString());
                healthBio.setCondition(cursor.getString(cursor.getColumnIndex(DBConstants.HEALTH_CONDITION)));
                healthBio.setConditionType(cursor.getString(cursor.getColumnIndex(DBConstants.HEALTH_CONDITION_TYPE)));

                healthbio_list.add(healthBio);


            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
        }
        return healthbio_list;
    }

}

