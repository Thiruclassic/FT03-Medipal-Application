package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Thirumal on 2/24/17.
 */

public interface ICEDao {
    public int addContact(InCaseofEmergencyContact contact);
    public List<InCaseofEmergencyContact> getContactsbyType(int type);
    public List<InCaseofEmergencyContact> getContactsbyPriority();
}
