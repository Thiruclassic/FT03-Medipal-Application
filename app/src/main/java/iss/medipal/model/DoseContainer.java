package iss.medipal.model;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.constants.Constants;
import iss.medipal.model.homeMedicineModels.ConsumptionDayModel;
import iss.medipal.model.homeMedicineModels.MedDayModel;
import iss.medipal.model.homeMedicineModels.MedDoseModel;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 19/3/17.
 */
public class DoseContainer {

    private static DoseContainer instance;

    private Activity mCurrentActivity;
    private SimpleDateFormat mTimeFormat;
    private SimpleDateFormat mDateFormat;
    private Calendar mCurrentDate;
    private Calendar mMedAddedDate;
    private Calendar mDoseCalendar;
    private List<Medicine> mMeds;
    private List<MedDayModel> mMedDayModel;
    private List<Consumption> mConsumption;
    private List<ConsumptionDayModel> mConsumtionDayModel;

    public static DoseContainer getInstance(Activity activity) {
        if (instance == null) {
            if (instance == null) {
                instance = new DoseContainer(activity);
            }
        } else {
            instance.setActivity(activity);
        }
        return instance;
    }

    private DoseContainer(Activity activity) {
        mDoseCalendar = Calendar.getInstance();
        mCurrentDate = Calendar.getInstance();
        mMedAddedDate = Calendar.getInstance();
        mCurrentActivity = activity;
        mMeds = MediPalApplication.getPersonStore().getmPersonalBio().getMedicines();
        mConsumption = MediPalApplication.getPersonStore().getmConsumptions();
        mTimeFormat = new SimpleDateFormat(Constants.TIME_FORMAT_STORAGE);
        mDateFormat = new SimpleDateFormat(Constants.ISSUE_DATE_FORMAT);
        reloadData(false);
    }

    private void setActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public void reloadData(boolean isReload) {
        if(!isReload) {
            if (AppHelper.isListEmpty(mConsumption)) {
                mConsumption = new ArrayList<>();
                mConsumtionDayModel = new ArrayList<>();
            } else {
                setConsumtion();
            }
        }
        if (mCurrentActivity != null) {
            mMedDayModel = new ArrayList<>();
            if (!AppHelper.isListEmpty(mMeds)) {
                ArrayList<MedDoseModel> mDayDoseRecords = new ArrayList<>();
                for (Medicine med : mMeds) {
                    mMedAddedDate.setTime(med.getDateIssued());
                    if(med.getReminder() != null){
                        Reminder rem = med.getReminder();
                        Date date = rem.getStartTime();
                        while (mMedAddedDate.before(mCurrentDate) || AppHelper.sameDay(mMedAddedDate, mCurrentDate)){
                            ArrayList<Consumption> consumptions = getCurrentDayConsumtion(mMedAddedDate);
                            mDoseCalendar.setTime(date);
                            for (int i = 0; i < rem.getFrequency(); i++) {
                                MedDoseModel medDoseModel = new MedDoseModel();
                                medDoseModel.setIdMed(med.getId());
                                medDoseModel.setDrugName(med.getMedicine());
                                medDoseModel.setDoseTime(
                                        mTimeFormat.format(mDoseCalendar.getTime()));
                                medDoseModel.setDate(mDateFormat.format(mMedAddedDate.getTime()));
                                mMedAddedDate.set(Calendar.HOUR_OF_DAY, mDoseCalendar.get(Calendar.HOUR_OF_DAY));
                                mMedAddedDate.set(Calendar.MINUTE, mDoseCalendar.get(Calendar.MINUTE));
                                Calendar consumtionCal = Calendar.getInstance();
                                if(!AppHelper.isListEmpty(consumptions)) {
                                    for (Consumption consumption : consumptions) {
                                        if (consumption.getMedicineId() == med.getId()) {
                                            consumtionCal.setTime(consumption.getConsumedOn());
                                            if (AppHelper.sameTime(mMedAddedDate, consumtionCal)) {
                                                medDoseModel.setStatus(Constants.TOOKIT_STATUS);
                                            }
                                        }
                                    }
                                }
                                mDoseCalendar.add(Calendar.HOUR, rem.getInterval());
                                mDayDoseRecords.add(medDoseModel);
                            }
                            mMedAddedDate.add(Calendar.DAY_OF_YEAR, 1);
                        }
                    }
                }
                for (MedDoseModel dr : mDayDoseRecords) {
                    addDoseRecord(dr);
                }
                sortAndUpdateData();
            }
        }
    }

    private ArrayList<Consumption> getCurrentDayConsumtion(Calendar date){
        for (ConsumptionDayModel consumptionDayModel: mConsumtionDayModel){
            if(AppHelper.sameDay(consumptionDayModel.getDate(),date)){
                return consumptionDayModel.getConsumptions();
            }
        }
        return new ArrayList<>();
    }


    public void addDoseRecord(MedDoseModel dr) {
        int id = dr.getId_med();
        String name = dr.getDrugName();
        Calendar date = AppHelper.getCalendarFromString(dr.getDate());

        boolean foundDMD = false;
        for (MedDayModel dmd : mMedDayModel) {
            if (dmd.getMedName().equalsIgnoreCase(name) && AppHelper.sameDay(date, dmd.getDate())) {
                dmd.getDoseRecordList().add(dr);
                dmd.reorderDoseRecordList();
                foundDMD = true;
                break;
            }
        }
        if (!foundDMD) {
            ArrayList<MedDoseModel> dList = new ArrayList<MedDoseModel>();
            date = AppHelper.getCalendarFromString(dr.getDate());
            dList.add(dr);

            // Gets the MyMed object for the specific medicine and
            // stores it in the DoseMedicine. This is done here instead
            // of the the constructor to avoid creating the med more than
            // once and wasting DB calls
            MedDayModel temDMD = new MedDayModel(id, name, date, dList);
            // Adds the DoseMedicineDay object
            mMedDayModel.add(temDMD);
        }
        sortAndUpdateData();
    }

    public void sortAndUpdateData() {
        Collections.sort(mMeds);
        for (MedDayModel dmd : mMedDayModel) {
            dmd.reorderDoseRecordList();
        }
    }

    public ArrayList<MedDayModel> getDosesForDay(Calendar cal){
        ArrayList<MedDayModel> doses = new ArrayList<>();
        for(MedDayModel dmd : mMedDayModel){
            if(AppHelper.sameDay(dmd.getDate(), cal)){
                doses.add(dmd);
            }
        }
        if(!AppHelper.isListEmpty(doses)){
            mCurrentDate = cal;
        }
        return doses;
    }

    public int getDosesCountForDay(Calendar cal){
        int count = 0;
        for(MedDayModel dmd : mMedDayModel){
            if(AppHelper.sameDay(dmd.getDate(), cal)){
                count++;
            }
        }
        return count;
    }

    private void setConsumtion(){
        mConsumtionDayModel = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(Consumption consumption :mConsumption) {
            int id = consumption.getMedicineId();
            cal.setTime(consumption.getConsumedOn());

            boolean foundDMD = false;
            for (ConsumptionDayModel dmd : mConsumtionDayModel) {
                if (AppHelper.sameDay(cal, dmd.getDate())) {
                    dmd.getConsumptions().add(consumption);
                    foundDMD = true;
                    break;
                }
            }
            if (!foundDMD) {
                ArrayList<Consumption> dList = new ArrayList<Consumption>();
                dList.add(consumption);

                // Gets the MyMed object for the specific medicine and
                // stores it in the DoseMedicine. This is done here instead
                // of the the constructor to avoid creating the med more than
                // once and wasting DB calls
                ConsumptionDayModel temDMD = new ConsumptionDayModel(cal);
                temDMD.setConsumptions(dList);
                // Adds the DoseMedicineDay object
                mConsumtionDayModel.add(temDMD);
            }
        }
    }


    public Calendar getCurrentDate() {
        return mCurrentDate;
    }
}
