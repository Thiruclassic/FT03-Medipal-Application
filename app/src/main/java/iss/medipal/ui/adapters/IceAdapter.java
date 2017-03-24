package iss.medipal.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.medipal.constants.Constants;
import iss.medipal.ui.fragments.DoctorFragment;
import iss.medipal.ui.fragments.FamilyFragment;
import iss.medipal.ui.fragments.FriendFragment;


/**
 * Created by Manish on 15/3/2017.
 */

public class IceAdapter extends FragmentPagerAdapter {


    FamilyFragment familyFragment;
    FriendFragment friendFragment;
    DoctorFragment doctorFragment;
        int numOfTabs;

        public IceAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(familyFragment==null) {
                        familyFragment = new FamilyFragment();
                    }
                    return familyFragment;

                case 1:
                    if(friendFragment==null) {
                       friendFragment = new FriendFragment();
                    }
                    return friendFragment;

                default:
                    if(doctorFragment==null) {
                        doctorFragment = new DoctorFragment();
                    }

                    return doctorFragment;
            }
        }

        @Override public int getCount() {
            return numOfTabs;

        }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constants.FAMILY;
            case 1:
                return Constants.FRIEND;
            case 2:
                return Constants.DOCTOR;
        }
        return null;
    }
}
