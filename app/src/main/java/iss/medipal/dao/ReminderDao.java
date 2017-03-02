package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Reminder;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface ReminderDao {
    public int addReminder(Reminder reminder);
    public int modifyReminder(Reminder reminder);
    public int deleteReminder(int reminderId);

    public List<Reminder> getAllReminders();

    public Reminder getReminderById(int reminderId);


}
