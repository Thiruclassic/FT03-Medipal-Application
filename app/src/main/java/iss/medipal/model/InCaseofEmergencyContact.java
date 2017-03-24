package iss.medipal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class InCaseofEmergencyContact implements Parcelable, Comparable<InCaseofEmergencyContact> {
    int contactId;
    String contactName;
    long contactNo;
    int contactType;
    String contactDescription;
    int contactSequence;

    public InCaseofEmergencyContact()
    {

    }

    protected InCaseofEmergencyContact(Parcel in)
    {
        contactId=in.readInt();
        contactName=in.readString();
        contactNo=in.readLong();
        contactType=in.readInt();
        contactDescription=in.readString();
        contactSequence=in.readInt();
    }

    public int getId() {
        return contactId;
    }

    public int getSequence() {
        return contactSequence;
    }

    public void setSequence(int contactSequence) {
        this.contactSequence = contactSequence;
    }

    public void setId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }

    public String getDescription() {
        return contactDescription;
    }

    public void setDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }

    /*@Override
    public int compareTo(@NonNull InCaseofEmergencyContact o) {  //
        return 0;
    }*/

    @Override
    public int compareTo(InCaseofEmergencyContact contact) {
        return this.getSequence() - contact.getSequence();
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof InCaseofEmergencyContact)
        {
            if(this.getId()==((InCaseofEmergencyContact) object).getId())
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contactId);
        dest.writeString(contactName);
        dest.writeLong(contactNo);
        dest.writeInt(contactType);
        dest.writeString(contactDescription);
        dest.writeInt(contactSequence);

    }

    public static final Creator<InCaseofEmergencyContact> CREATOR=new Creator<InCaseofEmergencyContact>() {
        @Override
        public InCaseofEmergencyContact createFromParcel(Parcel in) {
            return new InCaseofEmergencyContact(in);
        }

        @Override
        public InCaseofEmergencyContact[] newArray(int size) {
            return new InCaseofEmergencyContact[size];
        }
    };

}


