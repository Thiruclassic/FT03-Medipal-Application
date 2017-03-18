package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ReminderDao;
import iss.medipal.model.Reminder;

/**
 * Created by Naveen on 3/7/17.
 */

public class ReminderDaoImpl extends BaseDao implements ReminderDao {

    public static ReminderDaoImpl newInstance(Context context) {
        return new ReminderDaoImpl(context);
    }

    public ReminderDaoImpl(Context context){
        super(context);
    }

    @Override
    public int addReminder(Reminder reminder) {
        ContentValues values=new ContentValues();
        values.put("Frequency",reminder.getFrequency());
        values.put("StartTime",String.valueOf(reminder.getStartTime()));
        values.put("Interval",reminder.getInterval());
        int id=(int)database.insert(DBConstants.TABLE_REMINDER,null,values);
        Log.d("Insert  Reminder:",String.valueOf(id));
        return id;

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
        String query="Select * from "+ DBConstants.TABLE_REMINDER+ " where id=?";
        String[] args=new String[1];
        args[0]=String.valueOf(reminderId);
        Reminder reminder=new Reminder();
        try {
            Cursor cursor = database.rawQuery(query, args);

            Log.d("cursor",String.valueOf(cursor));

            if (cursor.moveToNext()) {

                Log.d("enter cursor","cursor entry");
                reminder.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                reminder.setFrequency(cursor.getInt(cursor.getColumnIndex("Frequency")));
                reminder.setInterval(cursor.getInt(cursor.getColumnIndex("Interval")));
                String startTime = cursor.getString(cursor.getColumnIndex("StartTime"));
                SimpleDateFormat dateFormat =new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
                Log.d("Timetime",startTime);
                reminder.setStartTime(dateFormat.parse(startTime));

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return reminder;
    }

    public Reminder getReminder() {
        String query="Select * from "+ DBConstants.TABLE_REMINDER;
       /* String[] args=new String[1];
        args[0]=String.valueOf(reminderId);*/
        Log.d("Timetime","dgdggfd");
        Reminder reminder=new Reminder();
        try {
            Cursor cursor = database.rawQuery(query, null);

            Log.d("cursor",String.valueOf(cursor));

            if (cursor.moveToNext()) {

                Log.d("enter cursor","cursor entry");
                reminder.setId(cursor.getInt(cursor.getColumnIndex("ID")));
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
            System.out.println(e.getMessage());
        }
        return reminder;
    }

}
