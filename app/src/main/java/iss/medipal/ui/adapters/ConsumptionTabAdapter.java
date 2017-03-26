package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.ui.fragments.ConsumptionDateFragment;
import iss.medipal.ui.fragments.ConsumptionMedFragment;
import iss.medipal.ui.fragments.SimpleCardFragment;
import iss.medipal.ui.fragments.ViewReminderFragment;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumptionTabAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] mTitles;

    public ConsumptionTabAdapter(Context context, FragmentManager fragmentManager)
    {
        super(fragmentManager);
        mContext = context;
        mTitles=new String[]{context.getString(R.string.by_date_text),
                context.getString(R.string.by_med_text)};
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ConsumptionDateFragment.newInstance();
            case 1:
                return ConsumptionMedFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
