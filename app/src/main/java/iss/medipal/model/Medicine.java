package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.widget.Adapter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Medicine implements Parcelable, Comparable<Medicine> {

    private int id;
    private int catId;
    private int reminderId;
    private int quantity;
    private int dosage;
    private int expireFactor;
    private int threshold;
    private String medicine;
    private String description;
    private boolean remind;
    private Date dateIssued;
    private Reminder reminder;

    public Medicine(){

    }

    protected Medicine(Parcel in) {
        id = in.readInt();
        catId = in.readInt();
        reminderId = in.readInt();
        quantity = in.readInt();
        dosage = in.readInt();
        expireFactor = in.readInt();
        threshold = in.readInt();
        medicine = in.readString();
        description = in.readString();
        boolean[] remindArray = new boolean[0];
        in.readBooleanArray(remindArray);
        remind = remindArray[0];
        dateIssued = (Date) in.readSerializable();
        reminder = in.readParcelable(Reminder.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public int getExpireFactor() {
        return expireFactor;
    }

    public void setExpireFactor(int expireFactor) {
        this.expireFactor = expireFactor;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }


    @Override
    public int compareTo(@NonNull Medicine o) {
        if(medicine.equalsIgnoreCase(o.getMedicine()))
            return 0;
        else
            return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(catId);
        parcel.writeInt(reminderId);
        parcel.writeInt(quantity);
        parcel.writeInt(dosage);
        parcel.writeInt(expireFactor);
        parcel.writeInt(threshold);
        parcel.writeString(medicine);
        parcel.writeString(description);
        parcel.writeBooleanArray(new boolean[]{remind});
        parcel.writeSerializable(dateIssued);
        parcel.writeParcelable(reminder, 0);
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
}
