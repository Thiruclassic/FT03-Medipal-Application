package iss.medipal.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.ConsumptionDao;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.PersonBioDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.ConsumptionDaoImpl;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.dao.impl.PersonBioDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.model.Category;
import iss.medipal.model.Consumption;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.HealthBio;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;
import iss.medipal.model.Reminder;
import iss.medipal.ui.customViews.RotateLoading;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */

public class SplashActivity extends BaseFullScreenActivity {

    private static final int SPLASH_TIME = 2000;
    private Handler mHandler;
    private RotateLoading mRotatingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRotatingProgress = (RotateLoading) findViewById(R.id.rotateloading);
        mRotatingProgress.start();
        mHandler = new Handler();
        if (SharedPreferenceManager.isAppInitialLaunch(SplashActivity.this)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRotatingProgress.stop();
                    launchActivity(TutorialActivity.class);
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
        private AppointmentDao mAppointmentDao;
        private HealthBioDao mHealthDao;

        public GetPersonTask(Context context) {
            this.mBioDao = new PersonBioDaoImpl(context);
            this.mMedicationDao = new MedicineDaoImpl(context);
            this.mRemiderDao = new ReminderDaoImpl(context);
            this.mConsumptionDao = new ConsumptionDaoImpl(context);
            this.mAppointmentDao = new AppointmentDaoImpl(context);
            this.mHealthDao = new HealthBioDaoImpl(context);
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
            ArrayList<Appointment>appointments =(ArrayList<Appointment>) mAppointmentDao.getAllAppointments();
            //todo appointments reminders sreekumar
            ArrayList<Consumption> consumptions = (ArrayList<Consumption>) mConsumptionDao.getAllConsumptions();
            List<HealthBio> healthBioList = mHealthDao.getAllHealthBio();
            result.setMedicines(meds);
            result.setAppointments(appointments);
            result.setHealthBios(healthBioList);
            mPersonStore.setmPersonalBio(result);
            mPersonStore.setmConsumptions(consumptions);
            DoseContainer.getInstance(SplashActivity.this);
            if(AppHelper.isListEmpty(meds) || meds.size() < 10) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mRotatingProgress.stop();
            launchActivity(MainActivity.class);
        }
    }


}
