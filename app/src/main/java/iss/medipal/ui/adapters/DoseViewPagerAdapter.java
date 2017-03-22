package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import iss.medipal.model.Medicine;
import iss.medipal.model.homeMedicineModels.MedDayModel;
import iss.medipal.ui.fragments.OnedayDoseFragment;

/**
 * Created by junaidramis on 19/3/17.
 */

public class DoseViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String ARGS_MEDS = "ARGS_MEDS";

    ArrayList<MedDayModel> mMedDoses;
    ArrayList<Medicine> mMeds;
    Calendar cal;

    public DoseViewPagerAdapter(FragmentManager fm, ArrayList<MedDayModel> medsDoses,
                                ArrayList<Medicine> meds, Calendar currentCal) {
        super(fm);
        mMedDoses = medsDoses;
        mMeds = meds;
        cal = currentCal;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 && getCount() == 1) {
            return OnedayDoseFragment.newInstance(mMedDoses.get(position), mMeds, cal, true, true);
        } else if (position == 0) {
            return OnedayDoseFragment.newInstance(mMedDoses.get(position), mMeds, cal, true, false);
        } else if(position == mMedDoses.size() - 1){
            return OnedayDoseFragment.newInstance(mMedDoses.get(position), mMeds, cal, false, true);
        } else
            return OnedayDoseFragment.newInstance(mMedDoses.get(position), mMeds, cal, false, false);
    }

    @Override
    public int getCount() {
        return mMedDoses.size();
    }
}

