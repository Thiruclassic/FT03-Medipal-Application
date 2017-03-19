package iss.medipal.model;

import android.content.Context;

import iss.medipal.asyncs.AddPersonBioTask;
import iss.medipal.dao.impl.PersonBioDaoImpl;

/**
 * Created by junaidramis on 10/3/17.
 */
public class PersonStore {

    private PersonalBio mPersonalBio;
    private Context mContext;
    private PersonBioDaoImpl mBioDao;

    //Tasks
    private AddPersonBioTask mAddPersonalBioTask;


    public PersonStore(PersonalBio personBio, Context context){
        this.mPersonalBio = personBio;
        this.mContext = context;
    }

    public void setmPersonalBio(PersonalBio mPersonalBio) {
        this.mPersonalBio = mPersonalBio;
    }

    public PersonalBio getmPersonalBio() {
        return mPersonalBio;
    }

    public void addPersonBio(PersonalBio personalBio){
        mPersonalBio.setName(personalBio.getName());
        mPersonalBio.setId(personalBio.getId());
        mPersonalBio.setDob(personalBio.getDob());
        mPersonalBio.setHeight(personalBio.getHeight());
        mPersonalBio.setBloodType(personalBio.getBloodType());
        mPersonalBio.setAddress(personalBio.getAddress());
        mPersonalBio.setName(personalBio.getPostalCode());
        mAddPersonalBioTask = new AddPersonBioTask(mContext);
        mAddPersonalBioTask.execute(personalBio);
    }


}
