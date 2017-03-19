package iss.medipal.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.activities.SplashActivity;

/**
 * Created by junaidramis on 17/3/17.
 */

public class GetCategoriesTask extends AsyncTask<Void, Void, ArrayList<Category>> {

    private CategoryDaoImpl mCategoryDao;

    public GetCategoriesTask(Context context) {
        this.mCategoryDao = new CategoryDaoImpl(context);
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        ArrayList<Category> categories = (ArrayList<Category>) mCategoryDao.getAllCategories();
        return categories;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> result) {
        MediPalApplication.getPersonStore().setCategory(result);
        mCategoryDao.close();
    }
}