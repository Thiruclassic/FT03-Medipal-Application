package iss.medipal;

import android.support.test.runner.AndroidJUnit4;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by manish on 26/3/17.
 */

@RunWith(AndroidJUnit4.class)
public class ICEUnitTest extends TestCase implements MedipalUnitTest {

    private InCaseofEmergencyContact contact;
    private ICEDao contactDao;
    private List<InCaseofEmergencyContact> contactList;

    @Before
    public void setUp() throws Exception {
        contact = new InCaseofEmergencyContact();
        contactDao = new IceDaoImpl(context);
        contactList = contactDao.getAllContacts();
    }

    @Test
    public void testCRUDContact() throws Exception
    {
        //testing contact Creation
        int id = 0;

        //contacts with null data should not be inserted
        assertEquals("Contact should not be added",-1,contactDao.addContact(contact));

        contact.setContactName("John Doe");
        contact.setContactNo(98765432);
        contact.setContactType(Constants.FAMILY_CONTACT);
        contact.setSequence(Constants.HIGH_PRIORITY);
        contact.setDescription("An important contact");
        id = contactDao.addContact(contact);
        contact.setId(id);
        assertNotEquals("Contact should be successfully added",-1,id);

        //testing contact Retrieval
        contactList = contactDao.getAllContacts();
        contact = contactList.get(0);
        InCaseofEmergencyContact contactDBData = contactDao.getContactbyId(contact.getId());

        //testing contact data values
        assertNotEquals(0,contact.getId());
        assertEquals("Contact data not retrieved",contact.getContactName(),contactDBData.getContactName());

        //testing contact Update
        contact = contactList.get(contactList.size()-1);
        contact.setContactName("Unicorn");
        assertNotEquals("Contact not updated",-1,contactDao.updateContact(contact));

        InCaseofEmergencyContact contactDbData = contactDao.getContactbyId(contact.getId());
        //Contact should exist
        assertNotNull("Contact object should not be null",contactDbData);

        //Updating contact details
        assertEquals("Contact not updated",contact.getContactName(),contactDbData.getContactName());

        //testing contact Delete
        contactDBData = contactList.get(contactList.size()-1);
        assertNotEquals("Medicine should be deleted",-1,contactDao.deleteContact(contactDBData.getId()));
    }

    public void testListSize()
    {
        List<InCaseofEmergencyContact> contacts = contactDao.getAllContacts();
        assertEquals("Contact list is not correct!",contactList,contacts);
    }
}
