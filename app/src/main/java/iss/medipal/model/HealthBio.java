package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class HealthBio implements Parcelable, Comparable<HealthBio> {
    int id;
    String condition;
    String startDate;
    String conditionType;


    protected HealthBio(Parcel in)
    {
        id = in.readInt();
        condition = in.readString();
        conditionType = in.readString();
        startDate = in.readString();
    }

    public HealthBio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(condition);
        parcel.writeString(conditionType);
        parcel.writeString(startDate);
    }

    @Override
    public int compareTo(HealthBio o) {
        if(condition.equalsIgnoreCase(o.getCondition()))
            return 0;
        else
            return 1;
    }

    public static final Creator<HealthBio> CREATOR = new Creator<HealthBio>() {
        @Override
        public HealthBio createFromParcel(Parcel in) {
            return new HealthBio(in);
        }

        @Override
        public HealthBio[] newArray(int size) {
            return new HealthBio[size];
        }
    };
}
