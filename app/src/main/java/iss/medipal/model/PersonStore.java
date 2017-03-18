package iss.medipal.model;

import android.content.Context;

import java.util.ArrayList;

import iss.medipal.asyncs.AddPersonBioTask;
import iss.medipal.asyncs.EditPersonalBioTask;
import iss.medipal.asyncs.GetCategoriesTask;
import iss.medipal.dao.impl.PersonBioDaoImpl;

/**
 * Created by junaidramis on 10/3/17.
 */
public class PersonStore {

    private PersonalBio mPersonalBio;
    private ArrayList<Category> mCategory;
    private Context mContext;
    private PersonBioDaoImpl mBioDao;

    //Tasks
    private AddPersonBioTask mAddPersonalBioTask;
    private EditPersonalBioTask mEditPersonalBioTask;
    private GetCategoriesTask mCategoriesTask;


    public PersonStore(PersonalBio personBio, Context context){
        this.mPersonalBio = personBio;
        this.mContext = context;
        new GetCategoriesTask(context).execute();
    }

    //getters/setters
    public void setmPersonalBio(PersonalBio mPersonalBio) {
        this.mPersonalBio = mPersonalBio;
    }

    public PersonalBio getmPersonalBio() {
        return mPersonalBio;
    }

    public ArrayList<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(ArrayList<Category> mCategory) {
        this.mCategory = mCategory;
    }

    //personal bio
    public void addPersonBio(PersonalBio personalBio){
        mPersonalBio.setName(personalBio.getName());
        mPersonalBio.setIdNo(personalBio.getIdNo());
        mPersonalBio.setDob(personalBio.getDob());
        mPersonalBio.setHeight(personalBio.getHeight());
        mPersonalBio.setBloodType(personalBio.getBloodType());
        mPersonalBio.setAddress(personalBio.getAddress());
        mPersonalBio.setPostalCode(personalBio.getPostalCode());
        mAddPersonalBioTask = new AddPersonBioTask(mContext);
        mAddPersonalBioTask.execute(personalBio);
    }

    public void editPersonalBio(PersonalBio personalBio){
        mPersonalBio.setId(personalBio.getId());
        mPersonalBio.setName(personalBio.getName());
        mPersonalBio.setIdNo(personalBio.getIdNo());
        mPersonalBio.setDob(personalBio.getDob());
        mPersonalBio.setHeight(personalBio.getHeight());
        mPersonalBio.setBloodType(personalBio.getBloodType());
        mPersonalBio.setAddress(personalBio.getAddress());
        mPersonalBio.setPostalCode(personalBio.getPostalCode());
        mEditPersonalBioTask = new EditPersonalBioTask(mContext);
        mEditPersonalBioTask.execute(personalBio);
    }
}