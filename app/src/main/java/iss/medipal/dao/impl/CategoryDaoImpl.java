package iss.medipal.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.constants.DatabaseConstants;
import iss.medipal.dao.CategoryDao;
import iss.medipal.helper.DatabaseHandler;
import iss.medipal.model.Category;
import iss.medipal.model.Medicine;
import iss.medipal.util.DatabaseUtility;

/**
 * Created by Naveen on 3/7/17.
 */

public class CategoryDaoImpl implements CategoryDao {

    DatabaseHandler dbHandler;

    public static CategoryDaoImpl newInstance() {
        CategoryDaoImpl fragment = new CategoryDaoImpl();
        fragment.dbHandler= DatabaseUtility.getDatabaseHandler();

        return fragment;
    }

    @Override
    public Category getCategoryByCode(String code) {
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return null;
    }

    @Override
    public List<String> getAllCategories() {

        String query="Select * from "+ DatabaseConstants.TABLE_CATEGORY;

        SQLiteDatabase database=dbHandler.getReadableDatabase();

        Cursor cursor=database.rawQuery(query,null);
        List<String> categories=new ArrayList<>();

        while (cursor.moveToNext())
        {
            String category=cursor.getString(1);
            categories.add(category);
        }

        return categories;
    }
}
