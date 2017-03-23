package iss.medipal.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
    public List<Category> getAllCategories() {
        String query="Select * from "+ DBConstants.TABLE_CATEGORY;
        Cursor cursor = database.rawQuery(query,null);
        List<Category> categories=new ArrayList<>();
        while (cursor.moveToNext())
        {
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setCategory(cursor.getString(cursor.getColumnIndex(DBConstants.CATEGORY_NAME)));
            category.setCode(cursor.getString(cursor.getColumnIndex(DBConstants.CATEGORY_CODE)));
            category.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.CATEGORY_DESCRIPTION)));
            category.setRemind(cursor.getInt(cursor.getColumnIndex(DBConstants.CATEGORY_REMIND)) > 0);
            categories.add(category);
        }

        return categories;
    }

    @Override
    public long addCategory(Category category) {

        ContentValues values=new ContentValues();
        values.put(DBConstants.CATEGORY_CODE,category.getCode());
        values.put(DBConstants.CATEGORY_NAME,category.getCategory());
        values.put(DBConstants.CATEGORY_DESCRIPTION,category.getDescription());
        values.put(DBConstants.CATEGORY_REMIND,category.isRemind());

        long id=database.insert(DBConstants.TABLE_CATEGORY,null,values);

        return id;
    }

    @Override
    public long updateCategory(Category category) {

        ContentValues values=new ContentValues();
        values.put(DBConstants.CATEGORY_CODE,category.getCode());
        values.put(DBConstants.CATEGORY_NAME,category.getCategory());
        values.put(DBConstants.CATEGORY_DESCRIPTION,category.getDescription());
        values.put(DBConstants.CATEGORY_REMIND,category.isRemind());
        int id=(int)database.update(DBConstants.TABLE_CATEGORY,values,"id=?",new String[]{String.valueOf(category.getId())});
        return id;
    }
}
