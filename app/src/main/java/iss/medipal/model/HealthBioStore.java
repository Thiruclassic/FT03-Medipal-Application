package iss.medipal.model;

import android.content.Context;

import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;

/**
 * Created by Mridul on 16-03-2017.
 */

public class HealthBioStore {



    private HealthBio mHealthBio;
    private Context mContext;
    private HealthBioDaoImpl mHealthBioDao;


    public HealthBioStore(HealthBio mHealthBio, Context mContext) {
        this.mHealthBio = mHealthBio;
        this.mContext = mContext;
    }
    public HealthBio getmHealthBio() {
        return mHealthBio;
    }

    public void setmHealthBio(HealthBio mHealthBio) {
        this.mHealthBio = mHealthBio;
    }

    public void addHealthBio(HealthBio HealthBio) {
        mHealthBio.setCondition(HealthBio.getCondition());
        mHealthBio.setConditionType(HealthBio.getConditionType());
        mHealthBio.setStartDate(HealthBio.getStartDate());

    }
}
