package iss.medipal;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Reminder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Thirumal on 26/3/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ReminderUnitTest extends TestCase implements MedipalUnitTest  {
    Reminder reminder;
    ReminderDao reminderDao;
    List<Reminder> reminderList;


    @Before
    public void setUp()
    {
        reminder=new Reminder();
        reminderDao=new ReminderDaoImpl(MedipalUnitTest.context);
        reminderList=reminderDao.getAllReminders();
    }
    @After
    public void close()
    {
        reminderDao.close();
    }

    @Test
    public void testAddReminder()
    {
        assertNotNull("Reminder should not be added",reminderDao.addReminder(reminder));

        reminder.setFrequency(1);
        reminder.setInterval(2);
        reminder.setStartTime(new Date());
        reminder=reminderDao.addReminder(reminder);
        assertNotEquals("Reminder should be added",-1,reminder.getId());

        //updating the medicine Details
        reminder.setFrequency(2);
        reminder.setInterval(4);
        assertNotNull("Reminder should be updated",reminderDao.modifyReminder(reminder));

        //Deleting the Medicine
        assertEquals("Reminder should be deleted",1,reminderDao.deleteReminder(reminder.getId()));
    }

    @Test
    public void testListSize()
    {
        List<Reminder> reminders=reminderDao.getAllReminders();
        assertEquals("List should be the same",reminderList,reminders);
    }


}
