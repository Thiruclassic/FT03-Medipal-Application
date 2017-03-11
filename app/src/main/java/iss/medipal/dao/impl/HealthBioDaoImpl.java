package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.model.HealthBio;

/**
 * Created by junaidramis on 11/3/17.
 */

public class HealthBioDaoImpl extends BaseDao implements HealthBioDao {


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
        return 0;
    }

    @Override
    public List<HealthBio> getAllHealthBio(int id) {
        return null;
    }
}
