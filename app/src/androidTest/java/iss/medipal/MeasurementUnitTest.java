package iss.medipal;

import android.support.test.espresso.base.BaseLayerModule_ProvideMainLooperFactory;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.model.Pulse;
import iss.medipal.model.Temperature;
import iss.medipal.model.Weight;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class MeasurementUnitTest extends TestCase implements MedipalUnitTest {

    BloodPressure bloodPressure;
    Weight weight;
    Temperature temperature;
    Pulse pulse;

    MeasurementDao measurementDao;

    @Before
    public void setUp() {
        bloodPressure = populateBloodPressure();
        weight=populateWeight();
        temperature=populateTempertaure();
        pulse=populatePulse();
        measurementDao = new MeasurementDaoImpl(context);

    }
    @After
    public void tearDown(){

        measurementDao=null;
        bloodPressure=null;
        weight=null;
        temperature=null;
        pulse=null;
    }
    @Test
    public void testAddBloodPressure(){

        assertNotEquals(-1,measurementDao.addBloodPressure(bloodPressure));
    }
    @Test
    public void testAddWeight(){

        assertNotEquals(-1,measurementDao.addWeight(weight));
    }
    @Test
    public void testAddTemperature(){

        assertNotEquals(-1,measurementDao.addTemperature(temperature));
    }
    @Test
    public void testAddPulse(){

        assertNotEquals(-1,measurementDao.addPulse(pulse));
    }

    @Test
    public void testGetBloodPressureValues(){

        assertNotNull(measurementDao.getBloodPressureValues());
    }
    @Test
    public void testGetWeightValues(){

        assertNotNull(measurementDao.getWeightValues());
    }
    @Test
    public void testGetTempValues(){

        assertNotNull(measurementDao.getTempValues());
    }
    @Test
    public void testGetPulseValues(){

        assertNotNull(measurementDao.getPulseValues());
    }
    @Test
    public void testDeleteMeasurement(){

        List<BloodPressure> bloodPressureList=measurementDao.getBloodPressureValues();
        assertNotEquals(-1,measurementDao.deleteMeasurement(bloodPressureList.get(0).getId()));
    }

    private BloodPressure populateBloodPressure(){

        int systolic=60;//mmmHg
        int diastolic=80;//mmHg
        BloodPressure measurement = new BloodPressure(systolic,diastolic,new Date());
        return measurement;
    }
    private Weight populateWeight(){

        int weight =80;//kg
        Weight measurement = new Weight(new Date(),weight);
        return measurement;

    }
    private Temperature populateTempertaure(){

        int temperature =80;//kg
        Temperature measurement = new Temperature(temperature,new Date());
        return measurement;

    }
    private Pulse populatePulse(){

        int pulseMes =80;//bpm
        Pulse measurement = new Pulse(new Date(),pulseMes);
        return measurement;

    }

}
