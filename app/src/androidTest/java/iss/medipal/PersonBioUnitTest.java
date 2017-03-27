package iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import iss.medipal.dao.PersonBioDao;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.PersonalBio;

/**
 * Created by junaidramis on 27/3/17.
 */
@RunWith(AndroidJUnit4.class)
public class PersonBioUnitTest extends TestCase implements MedipalUnitTest   {

    private PersonBioDao mDao;
    private PersonalBio bio;

    @Before
    public void setUp()
    {
        bio = new PersonalBio();
        mDao = new PersonBioDaoImpl(context);
    }

    @After
    public void close(){
        mDao.clearTable();
        mDao.close();
    }

    @Test
    public void testUpdatePersonBio(){
        assertNull(mDao.getPersonalBio());
        bio.setName("Juan");
        bio.setIdNo("A54739437");
        bio.setDob("12/10/96");
        bio.setBloodType("AB+");
        bio.setAddress("12-34, Building, Clementi, Singapore");
        bio.setHeight(11);
        bio.setPostalCode("21332");
        int id = mDao.createPersonalBio(bio);
        assertNotNull(mDao.getPersonalBio());
        bio.setId(id);
        bio.setName("Salv");
        mDao.updatePersonalBio(bio);
        PersonalBio dbBio = mDao.getPersonalBio();
        assertEquals(dbBio.getName(), "Salv");
    }

    @Test
    public void testAddPersonBio(){
        assertNull(mDao.getPersonalBio());
        bio = new PersonalBio();
        mDao.createPersonalBio(bio);
        assertNull(mDao.getPersonalBio());
        bio.setName("Juan");
        bio.setIdNo("A54739437");
        bio.setDob("12/10/96");
        bio.setBloodType("AB+");
        bio.setAddress("12-34, Building, Clementi, Singapore");
        bio.setHeight(11);
        mDao.createPersonalBio(bio);
        assertNull(mDao.getPersonalBio());
        bio.setPostalCode("21332");
        mDao.createPersonalBio(bio);
        assertNotNull(mDao.getPersonalBio());
    }

    @Test
    public void testClearTable(){
        bio.setName("Juan");
        bio.setIdNo("A54739437");
        bio.setDob("12/10/96");
        bio.setBloodType("AB+");
        bio.setAddress("12-34, Building, Clementi, Singapore");
        bio.setHeight(11);
        bio.setPostalCode("21332");
        mDao.createPersonalBio(bio);
        assertNotNull(mDao.getPersonalBio());
        mDao.clearTable();
        assertNull(mDao.getPersonalBio());
    }


}
