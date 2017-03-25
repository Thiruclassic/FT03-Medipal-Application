package iss.medipal.model;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import iss.medipal.asyncs.AddAppointmentTask;

import iss.medipal.MediPalApplication;
import iss.medipal.asyncs.AddConsumptionTask;
import iss.medipal.asyncs.AddHealthBioTask;
import iss.medipal.asyncs.AddUpdateContactTask;
import iss.medipal.asyncs.AddMedicineTask;
import iss.medipal.asyncs.AddPersonBioTask;
import iss.medipal.asyncs.AddReminderAlarmTask;
import iss.medipal.asyncs.DeleteHealthBioTask;
import iss.medipal.asyncs.EditAppointmentTask;
import iss.medipal.asyncs.AddUpdateCategoryTask;
import iss.medipal.asyncs.DeleteConsumptionTask;
import iss.medipal.asyncs.DeleteContactTask;
import iss.medipal.asyncs.DeleteMedicineTask;
import iss.medipal.asyncs.EditMedicineTask;
import iss.medipal.asyncs.EditPersonalBioTask;
import iss.medipal.asyncs.GetCategoriesTask;
import iss.medipal.asyncs.GetContactsTask;
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
    private AddAppointmentTask mAddAppointmentTask;
    private EditAppointmentTask mEditAppointmentTask;
    private DeleteConsumptionTask mDeleteConsumptionTask;
    private AddUpdateContactTask mAddUpdateContactTask;
    private DeleteContactTask deleteContactTask;
    private AddHealthBioTask mAddHealthBioTask;
    private DeleteHealthBioTask mDeleteHealthBioTask;



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
        List<Medicine> meds = mPersonalBio.getMedicines();
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
    public void addAppointment(Appointment appointment) {
        if (AppHelper.isListEmpty(mPersonalBio.getAppointments())) {
            mPersonalBio.setAppointments(new ArrayList<Appointment>());
        }
        mPersonalBio.getAppointments().add(appointment);
        mAddAppointmentTask = new AddAppointmentTask(mContext);
        mAddAppointmentTask.execute(appointment);
    }

    public void editAppointment(Appointment appointment) {
        List<Appointment> appointments = mPersonalBio.getAppointments();
        for (Appointment appointment1 : appointments) {
            if (appointment1.equals(appointment)) {
                appointment1.setId(appointment.getId());
                appointment1.setLocation(appointment.getLocation());
                appointment1.setDescription(appointment.getDescription());
                appointment1.setAppointment(appointment.getAppointment());
                break;
            }
        }
        mEditAppointmentTask = new EditAppointmentTask(mContext);
        mEditAppointmentTask.execute(appointment);
    }

    public void addConsumption(Consumption consumption) {
        if (AppHelper.isListEmpty(mConsumptions)) {
            mConsumptions = new ArrayList<>();
        }
        mConsumptions.add(consumption);
        mAddConsumptionTask = new AddConsumptionTask(mContext);
        mAddConsumptionTask.execute(consumption);
    }

    public void deleteConsumption(Consumption consumption){
        if(AppHelper.isListEmpty(mConsumptions)){
            return;
        }
        for(Consumption consumption1: mConsumptions) {
            if(consumption == consumption1){
                mConsumptions.remove(consumption1);
            }
            mDeleteConsumptionTask = new DeleteConsumptionTask(mContext);
            mDeleteConsumptionTask.execute(consumption);
        }
    }

    public void addUpdateCategory(Category category,boolean isEdit)
    {
        List<Category> categoryList=getCategory();

        if(!isEdit)
        {
            categoryList.add(category);
        }
        else
        {
            for(int i=0;i<categoryList.size();i++)
            {
                Category cat=categoryList.get(i);
                if(category.equals(cat))
                {
                    categoryList.set(i,category);
                    break;
                }
            }
        }
        AddUpdateCategoryTask addUpdateCategoryTask=new AddUpdateCategoryTask(mContext,isEdit);
        addUpdateCategoryTask.execute(category);

    }

    public void deleteMedicine(Medicine medicine)
    {
        List<Medicine> medicines=mPersonalBio.getMedicines();
        medicines.remove(medicine);
        DeleteMedicineTask deleteMedicineTask=new DeleteMedicineTask(mContext);
        deleteMedicineTask.execute(medicine);
    }
    public void addUpdateContact(InCaseofEmergencyContact contact,boolean isEdit){
        if(AppHelper.isListEmpty(mPersonalBio.getContacts())){
            mPersonalBio.setContacts(new ArrayList<InCaseofEmergencyContact>());
        }
        if(!isEdit) {
            mPersonalBio.getContacts().add(contact);

        }
        else
        {
            List<InCaseofEmergencyContact> contacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();
            for (int i = contacts.size() - 1; i >= 0; i--) {
                if (contact.equals(contacts.get(i))) {
                    contacts.set(i, contact);
                    break;
                }
            }
        }
        mAddUpdateContactTask = new AddUpdateContactTask(mContext,isEdit);
        mAddUpdateContactTask.execute(contact);
    }


    public void setContacts()
    {
        new GetContactsTask(mContext).execute();
    }

    public void deleteContact(InCaseofEmergencyContact contact)
    {
        mPersonalBio.getContacts().remove(contact);
        deleteContactTask = new DeleteContactTask(mContext);
        deleteContactTask.execute(contact);
    }

    public void addHealthBio(HealthBio healthBio){
        if(AppHelper.isListEmpty(mPersonalBio.getHealthBios())){
            mPersonalBio.setHealthBios(new ArrayList<HealthBio>());
        }
        mPersonalBio.getHealthBios().add(healthBio);
        mAddHealthBioTask = new AddHealthBioTask(mContext);
        mAddHealthBioTask.execute(healthBio);
    }

    public void deleteHealthBio(HealthBio healthBio)
    {
        List<HealthBio> medicines=mPersonalBio.getHealthBios();
        medicines.remove(healthBio);
        mDeleteHealthBioTask=new DeleteHealthBioTask(mContext);
        mDeleteHealthBioTask.execute(healthBio);
    }
}