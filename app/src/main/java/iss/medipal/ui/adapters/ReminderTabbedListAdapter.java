package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import iss.medipal.constants.Constants;
import iss.medipal.ui.fragments.SimpleCardFragment;
import iss.medipal.ui.fragments.ViewReminderFragment;

/**
 * Created by Thirumal on 20/3/2017.
 */

public class ReminderTabbedListAdapter extends FragmentPagerAdapter {


    String[] reminderNames;
    public ReminderTabbedListAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        reminderNames=new String[]{Constants.REMINDER_TAB_1,Constants.REMINDER_TAB_2,Constants.REMINDER_TAB_3};
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  ViewReminderFragment.newInstance();
            case 1:
                return SimpleCardFragment.getInstance("Screen : Appointment Reminders");
            case 2:
                return SimpleCardFragment.getInstance("Screen : Refill Reminders");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constants.REMINDER_TAB_1;
            case 1:
                return Constants.REMINDER_TAB_2;
            case 2:
                return Constants.REMINDER_TAB_3;
        }
        return null;
    }

}
