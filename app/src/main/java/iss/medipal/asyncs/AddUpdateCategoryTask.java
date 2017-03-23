package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import iss.medipal.dao.CategoryDao;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.model.Category;

/**
 * Created by Thirumal on 22/3/2017.
 */

public class AddUpdateCategoryTask extends AsyncTask<Category,Void,Long> {

    CategoryDao categoryDao;
    boolean isEdit;
    public AddUpdateCategoryTask(Context context)
    {
        this.categoryDao=new CategoryDaoImpl(context);
    }

    public AddUpdateCategoryTask(Context context,boolean isEdit)
    {
        this.categoryDao=new CategoryDaoImpl(context);
        this.isEdit=isEdit;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Category... params) {

        long id=0;
        if(params[0] instanceof Category) {
            if(isEdit)
            {
                categoryDao.updateCategory(params[0]);
            }
            else {
                id = categoryDao.addCategory(params[0]);
            }
        }
        return id;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}
