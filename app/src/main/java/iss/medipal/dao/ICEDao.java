package iss.medipal.dao;

import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.model.PersonalBio;

/**
 * Created by Thirumal on 2/24/17.
 */

public interface ICEDao {

    public int createContact(InCaseofEmergencyContact contact);

}
