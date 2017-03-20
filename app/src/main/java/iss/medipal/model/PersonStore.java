package iss.medipal.model;

import android.content.Context;

import java.util.ArrayList;

import iss.medipal.asyncs.AddConsumptionTask;
import iss.medipal.asyncs.AddMedicineTask;
import iss.medipal.asyncs.AddPersonBioTask;
import iss.medipal.asyncs.EditMedicineTask;
import iss.medipal.asyncs.EditPersonalBioTask;
import iss.medipal.asyncs.GetCategoriesTask;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 10/3/17.
 */
public class PersonStore {

    private PersonalBio mPersonalBio;
    private ArrayList<Category> mCategory;
    private ArrayList<Consumption> mConsumptions;
    private Context mContext;
    private PersonBioDaoImpl mBioDao;

    //Tasks
    private AddPersonBioTask mAddPersonalBioTask;
    private EditPersonalBioTask mEditPersonalBioTask;
    private AddMedicineTask mAddMedicineTask;
    private EditMedicineTask mEditMedicineTask;
    private AddConsumptionTask mAddConsumptionTask;

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

    public ArrayList<Consumption> getmConsumptions() {
        return mConsumptions;
    }

    public void setmConsumptions(ArrayList<Consumption> mConsumptions) {
        this.mConsumptions = mConsumptions;
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

    public void addMedicine(Medicine medicine){
        if(AppHelper.isListEmpty(mPersonalBio.getMedicines())){
            mPersonalBio.setMedicines(new ArrayList<Medicine>());
        }
        mPersonalBio.getMedicines().add(medicine);
        mAddMedicineTask = new AddMedicineTask(mContext);
        mAddMedicineTask.execute(medicine);
    }

    public void editMedicine(Medicine medicine){
        ArrayList<Medicine> meds = mPersonalBio.getMedicines();
        for(Medicine med: meds){
            if(med.equals(medicine)){
                med.setMedicine(medicine.getMedicine());
                med.setCatId(medicine.getCatId());
                med.setRemind(medicine.isRemind());
                med.setReminder(medicine.getReminder());
                med.setReminderId(medicine.getReminderId());
                med.setDosage(medicine.getDosage());
                med.setDescription(medicine.getDescription());
                med.setQuantity(medicine.getQuantity());
                med.setDateIssued(medicine.getDateIssued());
                med.setExpireFactor(medicine.getExpireFactor());
                med.setThreshold(medicine.getThreshold());
                break;
            }
        }
        mEditMedicineTask = new EditMedicineTask(mContext);
        mEditMedicineTask.execute(medicine);
    }

    public void addConsumption(Consumption consumption){
        if(AppHelper.isListEmpty(mConsumptions)){
            mConsumptions = new ArrayList<>();
        }
        mConsumptions.add(consumption);
        mAddConsumptionTask = new AddConsumptionTask(mContext);
        mAddConsumptionTask.execute(consumption);
    }
}