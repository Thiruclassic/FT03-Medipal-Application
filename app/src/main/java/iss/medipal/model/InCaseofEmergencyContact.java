package iss.medipal.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

import static android.R.attr.description;
import static android.R.attr.id;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class InCaseofEmergencyContact implements Comparator<InCaseofEmergencyContact> {
    int contactId;
    String contactName;
    long contactNo;
    int contactType;
    String contactDescription;
    int contactSequence;

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
    public int compare(InCaseofEmergencyContact contact1, InCaseofEmergencyContact contact2) {
        return contact1.getContactType() - contact2.getContactType();
    }
}


