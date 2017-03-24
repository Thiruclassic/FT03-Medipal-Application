package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Sreekumar on 3/21/2017.
 */

public class Appointment implements Parcelable, Comparable<Appointment> {

    private int id;
    private int reminderId;
    private String location;
    private Date appointment;
    private String description;
    private Reminder reminder;

    public Appointment() {


    }
    protected Appointment(Parcel in) {
        id = in.readInt();
        reminderId = in.readInt();
        location = in.readString();
        description = in.readString();
        appointment = (Date) in.readSerializable();
        reminder = in.readParcelable(Reminder.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppointment() {
        return appointment;
    }

    public void setAppointment(Date appointment) {
        this.appointment = appointment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(reminderId);
        parcel.writeString(location);
        parcel.writeSerializable(appointment);
        parcel.writeString(description);
        parcel.writeParcelable(reminder, 0);
    }

    @Override
    public int compareTo(@NonNull Appointment app) {
        if (location.equalsIgnoreCase(app.getLocation()))
            return 0;
        else
            return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

}
