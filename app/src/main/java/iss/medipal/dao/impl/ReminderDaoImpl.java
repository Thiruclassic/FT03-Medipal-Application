package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DatabaseConstants;
import iss.medipal.dao.ReminderDao;
import iss.medipal.helper.DatabaseHandler;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.util.DatabaseUtility;

/**
 * Created by Naveen on 3/7/17.
 */

public class ReminderDaoImpl implements ReminderDao {
    DatabaseHandler dbHandler;

    public static ReminderDaoImpl newInstance() {

        ReminderDaoImpl impl=new ReminderDaoImpl();
        impl.dbHandler= DatabaseUtility.getDatabaseHandler();

        return impl;
    }
    @Override
    public int addReminder(Reminder reminder) {

        SQLiteDatabase database=dbHandler.getWritableDatabase();

        Log.d("testing","testing");
        ContentValues values=new ContentValues();
        values.put("Frequency",reminder.getFrequency());
        values.put("StartTime",String.valueOf(reminder.getStartTime()));
        values.put("Interval",reminder.getInterval());
        Log.d("testing end","testing");
        int id=(int)database.insert(DatabaseConstants.TABLE_REMINDER,null,values);
        return reminder.getId();

    }

    @Override
    public int modifyReminder(Reminder reminder) {
        return 0;
    }

    @Override
    public int deleteReminder(int reminderId) {
        return 0;
    }

    @Override
    public List<Reminder> getAllReminders() {
        return null;
    }

    @Override
    public Reminder getReminderById(int reminderId) {
        String query="Select * from "+DatabaseConstants.TABLE_REMINDER+ " where id=?";
        SQLiteDatabase database=dbHandler.getWritableDatabase();
        String[] args=new String[1];
        args[0]=String.valueOf(reminderId);
        Log.d("Timetime","dgdggfd");
        Reminder reminder=new Reminder();
        try {
            Cursor cursor = database.rawQuery(query, args);

            Log.d("cursor",String.valueOf(cursor));

            if (cursor.moveToNext()) {

                Log.d("enter cursor","cursor entry");
                reminder.setId(reminderId);
                reminder.setFrequency(cursor.getInt(cursor.getColumnIndex("Frequency")));
                reminder.setInterval(cursor.getInt(cursor.getColumnIndex("Interval")));
                String startTime = cursor.getString(cursor.getColumnIndex("StartTime"));
                SimpleDateFormat dateFormat =new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Log.d("Timetime",startTime);
                reminder.setStartTime(dateFormat.parse(startTime));

            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
        return reminder;
    }
}
