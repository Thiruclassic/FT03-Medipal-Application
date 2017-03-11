package iss.medipal.dao.impl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.DBConstants;
import iss.medipal.dao.CategoryDao;
import iss.medipal.model.Category;

/**
 * Created by Naveen on 3/7/17.
 */

public class CategoryDaoImpl extends BaseDao implements CategoryDao {

    public static CategoryDaoImpl newInstance(Context context) {
        return new CategoryDaoImpl(context);
    }

    public CategoryDaoImpl(Context context){
        super(context);
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
        String query="Select * from "+ DBConstants.TABLE_CATEGORY;
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
