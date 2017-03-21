package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ICEDao;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 17/3/2017.
 */

public class IceDaoImpl extends BaseDao implements ICEDao{

    public IceDaoImpl(Context context) {
        super(context);
    }

    @Override
    public int addContact(InCaseofEmergencyContact contact) {
        ContentValues contentValues =  new ContentValues();
        contentValues.put(DBConstants.CONTACT_NAME,contact.getContactName());
        contentValues.put(DBConstants.CONTACT_NUMBER,contact.getContactNo());
        contentValues.put(DBConstants.CONTACT_DESCRIPTION,contact.getDescription());
        contentValues.put(DBConstants.CONTACT_TYPE,contact.getContactType());
        contentValues.put(DBConstants.CONTACT_SEQUENCE,contact.getSequence());
        int id=(int)database.insert(DBConstants.TABLE_ICE,null,contentValues);
        Log.d("inserting Contact",String.valueOf(id));
        return id;
    }

    @Override
    public List<InCaseofEmergencyContact> getContactsbyType(int type) {
        String query = "Select * from " + DBConstants.TABLE_ICE + "where ID=?";
        String args[] = new String[1];
        args[0] = String.valueOf(type);
        List<InCaseofEmergencyContact> contacts= new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(query,args);

            if(cursor.moveToNext())
            {
                InCaseofEmergencyContact contact = new InCaseofEmergencyContact();
                contact.setId(cursor.getInt(0));
                contact.setContactName(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_NAME)));
                contact.setContactNo(cursor.getLong(cursor.getColumnIndex(DBConstants.CONTACT_NUMBER)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_DESCRIPTION)));
                contact.setContactType(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_TYPE)));
                contact.setSequence(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_SEQUENCE)));
                contacts.add(contact);
            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }

        return contacts;
    }

    @Override
    public List<InCaseofEmergencyContact> getContactsbyPriority() {
        String query = "Select * from " + DBConstants.TABLE_ICE + "order by ContactSequence asc";
        List<InCaseofEmergencyContact> contacts= new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(query,null);

            if(cursor.moveToNext())
            {
                InCaseofEmergencyContact contact = new InCaseofEmergencyContact();
                contact.setId(cursor.getInt(0));
                contact.setContactName(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_NAME)));
                contact.setContactNo(cursor.getLong(cursor.getColumnIndex(DBConstants.CONTACT_NUMBER)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_DESCRIPTION)));
                contact.setContactType(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_TYPE)));
                contact.setSequence(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_SEQUENCE)));
                contacts.add(contact);
            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
        return contacts;
    }

    @Override
    public List<InCaseofEmergencyContact> getAllContacts() {
        String query = "Select * from " + DBConstants.TABLE_ICE ;
        List<InCaseofEmergencyContact> contacts= new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(query,null);

            if(cursor.moveToNext())
            {
                InCaseofEmergencyContact contact = new InCaseofEmergencyContact();
                contact.setId(cursor.getInt(0));
                contact.setContactName(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_NAME)));
                contact.setContactNo(cursor.getLong(cursor.getColumnIndex(DBConstants.CONTACT_NUMBER)));
                contact.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.CONTACT_DESCRIPTION)));
                contact.setContactType(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_TYPE)));
                contact.setSequence(cursor.getInt(cursor.getColumnIndex(DBConstants.CONTACT_SEQUENCE)));
                contacts.add(contact);
            }
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }

        return contacts;
    }
}
