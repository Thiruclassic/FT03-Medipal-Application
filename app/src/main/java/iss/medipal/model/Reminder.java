package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Reminder implements Parcelable{

    int id;
    int frequency;
    Date startTime;
    int Interval;

    public Reminder(){

    }
    public Reminder(int frequency,int interval,Date startTime){
        this.frequency=frequency;
        this.Interval=interval;
        this.startTime=startTime;

    }

    protected Reminder(Parcel in) {
        id = in.readInt();
        frequency = in.readInt();
        Interval = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(frequency);
        dest.writeInt(Interval);
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };
}
