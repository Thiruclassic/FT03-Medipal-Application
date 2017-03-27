package iss.medipal;

import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Mridul on 26-03-2017.
 */

public class HealthBioUnitTest extends TestCase implements MedipalUnitTest {

    HealthBio healthBio;
    HealthBioDao healthBioDao;
    List<HealthBio> healthBioList;

    @Before
    public void setUp()
    {
        healthBio = new HealthBio();
        healthBioDao = new HealthBioDaoImpl(MedipalUnitTest.context);
        healthBioList = healthBioDao.getAllHealthBio();
    }


    @Test
    public void testAddhealthBio() throws Exception {
        //  Adding Healthbio Test
        int id=0;

        //Healthbio with null data should not be inserted
        assertEquals("Healthbio should not be added",-1,healthBioDao.createHealthBio(healthBio));

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");

        healthBio.setCondition("FEVER");
        healthBio.setConditionType("CONDITION");
        healthBio.setStartDate(dateFormat.format(new Date()));
        id=healthBioDao.createHealthBio(healthBio);
        healthBio.setId(id);
        assertNotEquals("Healthbio should be added successfully",0,id);


        //testing deletion of medicine
        healthBioList = healthBioDao.getAllHealthBio();
        HealthBio healthBiodBData=healthBioList.get(healthBioList.size()-1);
        assertNotEquals("HealthBio should be deleted",0,healthBioDao.deleteHealthBio(healthBiodBData.getId()));
    }

    @Test
    public void testCheckListSize()
    {
        List<HealthBio> healthBios=healthBioDao.getAllHealthBio();

        assertEquals("HealthBio List is not correct",healthBioList,healthBios);
    }






}
