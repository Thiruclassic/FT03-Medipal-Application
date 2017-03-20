package iss.medipal.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.InCaseofEmergencyContact;
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

        private PersonBioDaoImpl mBioDao;
        private MedicineDaoImpl mMedicationDao;
        private ReminderDaoImpl mRemiderDao;
        private PersonStore mPersonStore;
        private IceDaoImpl mIceDao;

        public GetPersonTask(Context context) {
            this.mBioDao = new PersonBioDaoImpl(context);
            this.mMedicationDao = new MedicineDaoImpl(context);
            this.mRemiderDao = new ReminderDaoImpl(context);
            this.mIceDao = new IceDaoImpl(context);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mPersonStore = MediPalApplication.getPersonStore();
            PersonalBio result = mBioDao.getPersonalBio();
            ArrayList<InCaseofEmergencyContact> contacts = (ArrayList<InCaseofEmergencyContact>) mIceDao.getAllContacts();
            result.setContacts(contacts);
            ArrayList<Medicine> meds = (ArrayList<Medicine>) mMedicationDao.getAllMedicines();
            for(Medicine med: meds){
                Reminder rem = mRemiderDao.getReminderById(med.getReminderId());
                if(rem != null){
                    med.setReminder(rem);
                }
            }
            result.setMedicines(meds);
            mPersonStore.setmPersonalBio(result);
            try {
                Thread.sleep(2000);
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
