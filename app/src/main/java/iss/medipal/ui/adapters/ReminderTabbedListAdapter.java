package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.model.Medicine;
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
                return  ViewReminderFragment.newInstance(DBConstants.TABLE_MEDICINE);
            case 1:
                return SimpleCardFragment.getInstance("Screen : Appointment Reminders");
            case 2:
                return ViewReminderFragment.newInstance(DBConstants.TABLE_REMINDER);
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
