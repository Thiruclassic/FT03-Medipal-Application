package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.ui.fragments.AddMedicineFragment;
import iss.medipal.ui.fragments.AppointmentFragment;
import iss.medipal.ui.fragments.SimpleCardFragment;
import iss.medipal.ui.fragments.ViewMedicineFragment;


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
        addFragments();
    }


    public void addFragments()
    {
        for (String title : mTitles) {
            switch (title) {
                case "My Meds":
                    mFragments.add(ViewMedicineFragment.newInstance("string1", "string2"));
                    break;
                case "Appointments":
                    mFragments.add(AppointmentFragment.newInstance("string1", "string2"));
                    break;
                    default:
                    mFragments.add(SimpleCardFragment.getInstance("Screen : " + title));
                }
            }

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
        return mFragments.get(position);
    }
}
