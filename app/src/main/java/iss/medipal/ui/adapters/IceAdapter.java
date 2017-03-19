package iss.medipal.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.medipal.ui.fragments.DoctorFragment;
import iss.medipal.ui.fragments.FamilyFragment;
import iss.medipal.ui.fragments.FriendFragment;


/**
 * Created by Manish on 15/3/2017.
 */

public class IceAdapter extends FragmentStatePagerAdapter {


        int numOfTabs;

        public IceAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FamilyFragment familyFragment = new FamilyFragment();
                    return familyFragment;

                case 1:
                    FriendFragment friendFragment = new FriendFragment();
                    return friendFragment;

                default:
                    DoctorFragment doctorFragment = new DoctorFragment();
                    return doctorFragment;
            }
        }

        @Override public int getCount() {
            return numOfTabs;

        }
}
