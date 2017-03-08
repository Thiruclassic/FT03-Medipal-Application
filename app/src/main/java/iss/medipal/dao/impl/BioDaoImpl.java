package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import iss.medipal.constants.DatabaseConstants;
import iss.medipal.dao.BioDao;
import iss.medipal.helper.DatabaseHandler;
import iss.medipal.model.HealthBio;
import iss.medipal.model.PersonalBio;
import iss.medipal.util.DatabaseUtility;

/**
 * Created by Naveen on 2/24/17.
 */

public class BioDaoImpl implements BioDao {

    DatabaseHandler dbHandler;

    // Insert Personal Bio Data
    @Override
    public int createPersonalBio(PersonalBio bio) {

        dbHandler= DatabaseUtility.getDatabaseHandler();
        SQLiteDatabase database=dbHandler.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("Name",bio.getName());
        values.put("DOB",bio.getDob().toString());
        values.put("IDNo",bio.getIdNo());
        values.put("Address",bio.getAddress());
        values.put("PostalCode",bio.getPostalCode());
        values.put("Height",bio.getHeight());
        values.put("BloodType",bio.getBloodType());

        int id=(int)database.insert(DatabaseConstants.TABLE_PERSONAL_BIO,null,values);
        return id;
    }

    @Override
    public int updatePersonalBio(PersonalBio bioData) {
        return 0;
    }

    @Override
    public int deletePersonalBio(int id) {
        return 0;
    }

    @Override
    public PersonalBio findpersonalBiobyId(int id) {
        return null;
    }

    // Insert Health Bio Data
    @Override
    public int createHealthBio(HealthBio healthBio) {
        dbHandler= DatabaseUtility.getDatabaseHandler();
        SQLiteDatabase database=dbHandler.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("Condition",healthBio.getCondition());
        values.put("StartDate",healthBio.getStartDate().toString());
        values.put("ConditionType",healthBio.getConditionType());

        int id=(int)database.insert(DatabaseConstants.TABLE_HEALTH_BIO,null,values);
        return id;
    }

    @Override
    public int updateHealthBio(HealthBio bioData) {
        return 0;
    }

    @Override
    public int deleteHealthBio(int id) {
        return 0;
    }

    @Override
    public List<HealthBio> getAllHealthBio(int id) {
        return null;
    }
}
