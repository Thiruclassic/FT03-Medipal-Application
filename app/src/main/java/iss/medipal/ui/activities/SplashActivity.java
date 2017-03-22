package iss.medipal.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.PersonBioDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.Consumption;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;
import iss.medipal.model.Reminder;
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

    private class GetPersonTask extends AsyncTask<Void, Void, Void> {

        private PersonBioDao mBioDao;
        private MedicineDao mMedicationDao;
        private ReminderDao mRemiderDao;
        private ConsumptionDao mConsumptionDao;
        private PersonStore mPersonStore;

        public GetPersonTask(Context context) {
            this.mBioDao = new PersonBioDaoImpl(context);
            this.mMedicationDao = new MedicineDaoImpl(context);
            this.mRemiderDao = new ReminderDaoImpl(context);
            this.mConsumptionDao = new ConsumptionDaoImpl(context);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mPersonStore = MediPalApplication.getPersonStore();
            PersonalBio result = mBioDao.getPersonalBio();
            ArrayList<Medicine> meds = (ArrayList<Medicine>) mMedicationDao.getAllMedicines();
            for(Medicine med: meds){
                Reminder rem = mRemiderDao.getReminderById(med.getReminderId());
                if(rem != null){
                    med.setReminder(rem);
                }
            }
            ArrayList<Consumption> consumptions = (ArrayList<Consumption>) mConsumptionDao.getAllConsumptions();
            result.setMedicines(meds);
            mPersonStore.setmPersonalBio(result);
            mPersonStore.setmConsumptions(consumptions);
            DoseContainer.getInstance(SplashActivity.this);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            launchActivity(MainActivity.class);
        }
    }


}
