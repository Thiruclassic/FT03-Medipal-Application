package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.model.PersonalBio;

/**
 * Created by Thirumal on 2/24/17.
 */

public interface ICEDao {

    public int addContact(InCaseofEmergencyContact contact);
    public int updateContact(InCaseofEmergencyContact contact);
    public int deleteContact(int contactId);
    public List<InCaseofEmergencyContact> getContactsbyPriority();
    public List<InCaseofEmergencyContact> getAllContacts();
    public InCaseofEmergencyContact getContactbyId(int contactId);
    public void close();


}
