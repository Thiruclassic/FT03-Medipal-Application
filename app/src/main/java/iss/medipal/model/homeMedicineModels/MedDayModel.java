package iss.medipal.model.homeMedicineModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import iss.medipal.model.Medicine;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 19/3/17.
 */

public class MedDayModel implements Parcelable {

    int medId;
    String medName;
    ArrayList<MedDoseModel> doseRecordList;
    Calendar date;
    Medicine med;

    public MedDayModel(int id, String name, Calendar currDate,
                           ArrayList<MedDoseModel> l) {
        medId = id;
        medName = name;
        date = currDate;
        doseRecordList = l;
    }


    private MedDayModel(Parcel in){
        this.medId = in.readInt();
        this.medName = in.readString();
        this.doseRecordList = in.readArrayList(null);
        this.date = (Calendar) in.readSerializable();
        this.med = in.readParcelable(null);
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public ArrayList<MedDoseModel> getDoseRecordList() {
        return doseRecordList;
    }

    public void setDoseRecordList(ArrayList<MedDoseModel> doseRecordList) {
        this.doseRecordList = doseRecordList;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Medicine getMed() {
        return med;
    }

    public void setMed(Medicine med) {
        this.med = med;
    }

    public void reorderDoseRecordList() {
        Collections.sort(doseRecordList, new Comparator<MedDoseModel>() {
            public int compare(MedDoseModel dr1, MedDoseModel dr2) {
                if(AppHelper.getTimeFromString(dr1.getDoseTime()).before(
                        AppHelper.getTimeFromString(dr2.getDoseTime()
                ))) {
                    return 1;
                } else return 0;
            }
        });
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(medId);
        parcel.writeString(medName);
        parcel.writeList(doseRecordList);
        parcel.writeSerializable(date);
        parcel.writeParcelable(med , 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedDayModel> CREATOR = new Creator<MedDayModel>() {
        @Override
        public MedDayModel createFromParcel(Parcel in) {
            return new MedDayModel(in);
        }

        @Override
        public MedDayModel[] newArray(int size) {
            return new MedDayModel[size];
        }
    };

}
