package iss.medipal.dao.impl;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import iss.medipal.helper.DatabaseHandler;

/**
 * Created by junaidramis on 10/3/17.
 */

public class BaseDao {
    protected SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    private Context mContext;

    public BaseDao(Context context){
        this.mContext = context;
        dbHelper = DatabaseHandler.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseHandler.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
