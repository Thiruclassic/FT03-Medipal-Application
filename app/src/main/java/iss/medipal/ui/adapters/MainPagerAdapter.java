package iss.medipal.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import iss.medipal.ui.fragments.SimpleCardFragment;


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
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Screen : " + title));
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
