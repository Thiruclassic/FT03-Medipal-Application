package iss.medipal;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.model.Medicine;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by manish on 26/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class ICEUnitTest extends TestCase implements MedipalUnitTest {

    InCaseofEmergencyContact contact;
    ICEDao contactDao;
    List<InCaseofEmergencyContact> contactList;

    @Override
    protected void setUp() throws Exception {
        contact = new InCaseofEmergencyContact();
        contactDao = new IceDaoImpl(InstrumentationRegistry.getContext());
        contactList = contactDao.getAllContacts();
    }

    @Test
    public void testAddContact() throws Exception
    {
        //testing contact Creation
        int id = 0;

        //contacts with null data should not be inserted
        assertEquals("Contact should not be added",0,contactDao.addContact(contact));

        contact.setContactName("John Doe");
        contact.setContactNo(98765432);
        contact.setContactType(Constants.FAMILY_CONTACT);
        contact.setSequence(Constants.HIGH_PRIORITY);
        contact.setDescription("An important contact");
        id = contactDao.addContact(contact);
        contact.setId(id);
        assertNotEquals("Contact should be successfully added",0,id);
    }

    @Test
    public void testContactData()
    {
        //testing contact Retrieval
        InCaseofEmergencyContact contactDBData = contactDao.getContactbyId(contact.getId());

        contact = contactList.get(0);
        contactDBData = contactDao.getContactbyId(contact.getId());

        //testing contact data with modified values
        assertNotEquals(0,contact.getId());
        assertEquals("Contact data not retrieved",contact.getContactName(),contactDBData.getContactName());
    }

    @Test
    public void testUpdateContact()
    {
        //testing contact Update
        contact = contactList.get(contactList.size()-1);
        contact.setContactName("Unicorn");

        assertNotEquals("Contact not updated",0,contactDao.updateContact(contact));

        InCaseofEmergencyContact contactDbData = contactDao.getContactbyId(contact.getContactType());
        //Contact should exist
        assertNotNull("Contact object should not be null",contactDbData);

        //Updating contact details
        assertEquals("Contact not updated",contact.getContactName(),contactDbData.getContactName());
    }

    @Test
    public void testDeleteMedicine()
    {
        //testing contact Delete
        InCaseofEmergencyContact contactDBData = contactList.get(contactList.size()-1);

        assertEquals("Medicine should not be deleted",0,contactDao.deleteContact(contact.getId()));
        assertNotEquals("Medicine should be deleted",0,contactDao.deleteContact(contactDBData.getId()));
    }
}
