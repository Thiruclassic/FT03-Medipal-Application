package iss.medipal.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */

public class SplashActivity extends BaseFullScreenActivity {

    private static final int SPLASH_TIME = 2000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        if (SharedPreferenceManager.isAppInitialLaunch(SplashActivity.this)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    launchActivity(UserProfileActivity.class);
                }
            }, SPLASH_TIME);
        } else {
            GetPersonTask task = new GetPersonTask(this);
            task.execute();
        }
    }

    private class GetPersonTask extends AsyncTask<Void, Void, PersonalBio> {

        private PersonalBio mPersonalBio = null;
        private PersonBioDaoImpl mBioDao;
        private MedicineDaoImpl mMedicationDao;

        public GetPersonTask(Context context) {
            this.mBioDao = new PersonBioDaoImpl(context);
            this.mMedicationDao = new MedicineDaoImpl(context);
        }

        @Override
        protected PersonalBio doInBackground(Void... params) {
            PersonalBio result = mBioDao.getPersonalBio();
            result.setMedicines((ArrayList<Medicine>) mMedicationDao.getAllMedicines());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(PersonalBio result) {
            mPersonalBio = result;
            PersonStore personStore = ((MediPalApplication)getApplicationContext()).getPersonStore();
            personStore.setmPersonalBio(mPersonalBio);
            mBioDao.close();
            mMedicationDao.close();
            launchActivity(MainActivity.class);
        }
    }


}
