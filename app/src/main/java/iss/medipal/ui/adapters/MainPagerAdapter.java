package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import iss.medipal.ui.fragments.AppointmentFragment;
import iss.medipal.ui.fragments.MoreFragment;
import iss.medipal.ui.fragments.SimpleCardFragment;
import iss.medipal.ui.fragments.ViewMedicineFragment;
import iss.medipal.ui.fragments.ViewTabbedReminderFragment;


/**
 * Created by junaidramis on 23/2/17.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;
    private ArrayList<Fragment> mFragments;


    public MainPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SimpleCardFragment.getInstance("Screen : " + mTitles[position]);
            case 1:
                return ViewMedicineFragment.newInstance();
            case 2:
                return AppointmentFragment.newInstance();
            case 3:
                return ViewTabbedReminderFragment.newInstance();
            case 4:
                return MoreFragment.newInstance();
        }
        return null;
    }
}
