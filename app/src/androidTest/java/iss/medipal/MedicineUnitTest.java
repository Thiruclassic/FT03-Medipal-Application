package iss.medipal;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;

import static org.junit.Assert.*;

/**
 * Created by Thirumal on 26/3/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MedicineUnitTest extends TestCase implements MedipalUnitTest {

    Medicine medicine;
    Reminder reminder;
    MedicineDao medicineDao;
    List<Medicine> medicineList;


    @BeforeClass
    public void setUp()
    {
            medicine = new Medicine();
            reminder = new Reminder();
            medicineDao = new MedicineDaoImpl(InstrumentationRegistry.getContext());
            medicineList = medicineDao.getAllMedicines();
    }


    @Test
    public void testAddMedicine() throws Exception {
        //  Adding Medicine Test
            int id=0;

            //medicine with null data should not be inserted
            assertEquals("Medicine should not be added",0,medicineDao.addMedicine(medicine));
            medicine.setReminderId(reminder.getId());
            medicine.setRemind(Boolean.TRUE);
            medicine.setDosage(2);
            medicine.setQuantity(100);
            medicine.setMedicine("Amlong");
            medicine.setThreshold(10);
            medicine.setDateIssued(new Date());
            medicine.setExpireFactor(10);
            id=medicineDao.addMedicine(medicine);
            medicine.setId(id);
            assertNotEquals("Medicine should be added successfully",0,id);
    }

    @Test
    public void testMedicineData() throws Exception {

        Medicine medicineDbData=medicineDao.getMedicinebyId(medicine.getId());


        medicine=medicineList.get(0);
        medicineDbData = medicineDao.getMedicinebyId(medicine.getId());

        //testing Medicine data with updated values
        assertNotEquals(0,medicine.getId());
        assertEquals("Medicine Data not retrieved",medicine.getMedicine(),medicineDbData.getMedicine());

    }

    @Test
    public void testUpdateMedicine() {

        medicine=medicineList.get(medicineList.size()-1);
        medicine.setMedicine("Amlong 5mg");


        assertNotEquals("Medicine Not Updated successfully",0,medicineDao.updateMedicine(medicine));

        Medicine medicineDbData=medicineDao.getMedicinebyId(medicine.getId());
        //Medicine Object should exist
        assertNotNull("Medicine Object  should not be null",medicineDbData);

        // Updating the specific details of the Medicine
        assertEquals("Medicine Data not successfully updated",medicine.getMedicine(),medicineDbData.getMedicine());

    }

    @Test
    public void testCheckListSize()
    {
        List<Medicine> medicines=medicineDao.getAllMedicines();

        assertEquals("Medicine List is not correct",medicineList,medicines);
    }

    @Test
    public void testDeleteMedicine()
    {
        Medicine medicinedBData=medicineList.get(medicineList.size()-1);

        assertEquals("Medicine should not be deleted",0,medicineDao.deleteMedicine(medicine.getId()));
        assertNotEquals("Medicine should be deleted",0,medicineDao.deleteMedicine(medicinedBData.getId()));
    }
}
