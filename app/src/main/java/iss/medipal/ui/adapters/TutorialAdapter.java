package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.medipal.ui.fragments.TutorialFragment;

/**
 * Created by junaidramis on 25/3/17.
 */

public class TutorialAdapter extends FragmentStatePagerAdapter {

    static final int NUM_PAGES = 5;

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TutorialFragment tp = null;
                tp = TutorialFragment.newInstance(position);

        return tp;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
