package iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.PersonBioDao;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.Consumption;
import iss.medipal.model.PersonalBio;

/**
 * Created by junaidramis on 27/3/17.
 */
@RunWith(AndroidJUnit4.class)
public class ConsumptionUnitTest extends TestCase implements MedipalUnitTest{

    private ConsumptionDao mDao;

    @Before
    public void setUp()
    {
        mDao = new ConsumptionDaoImpl(context);
        mDao.clearTable();
    }

    @After
    public void close(){
        mDao.clearTable();
        mDao.close();
    }

    @Test
    public void testCreateConsumtion(){
        assertEquals(mDao.getAllConsumptions().size(), 0);
        mDao.createConsumtion(getConsumptionObject(2, 3, null));
        assertEquals("Consumption should not be added", mDao.getAllConsumptions().size(), 0);
        Calendar cal = Calendar.getInstance();
        mDao.createConsumtion(getConsumptionObject(2, 3, cal.getTime()));
        assertEquals("Consumption succesfully added", mDao.getAllConsumptions().size(), 1);
        for (int i = 0; i < 5; i++){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDao.createConsumtion(getConsumptionObject(i+2, i+1, cal.getTime()));
        }
        assertEquals("Consumptions succesfully added", mDao.getAllConsumptions().size(), 6);
    }

    @Test
    public void testDeleteConsumption(){
        ArrayList<Consumption> consumptions = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 5; i++){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            Consumption consumption = getConsumptionObject(i+2, i+1, cal.getTime());
            consumptions.add(consumption);
            mDao.createConsumtion(consumption);
        }
        assertEquals("5 Consumptions succesfully added", mDao.getAllConsumptions().size(), 5);
        mDao.deleteConsumtion(consumptions.get(4));
        assertEquals("1 Consumption successfully deleted", mDao.getAllConsumptions().size(), 4);
        mDao.deleteConsumtionByMedId(consumptions.get(1).getMedicineId());
        assertEquals("1 Consumption successfully deleted by medicine id", mDao.getAllConsumptions().size(), 3);
        mDao.clearTable();
        assertEquals("Table cleared", mDao.getAllConsumptions().size(), 0);
    }

    private Consumption getConsumptionObject(int med, int quantity, Date consumedOn){
        Consumption consumption = new Consumption();
        consumption.setMedicineId(med);
        consumption.setQuantity(quantity);
        consumption.setConsumedOn(consumedOn);
        return consumption;
    }
}
