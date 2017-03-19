package iss.medipal.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iss.medipal.R;
import iss.medipal.ui.adapters.IceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_category, container, false);

        final ViewPager viewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
        IceAdapter pagerAdapter = new IceAdapter(getFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) fragmentView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Family"));
        tabLayout.addTab(tabLayout.newTab().setText("Friend"));
        tabLayout.addTab(tabLayout.newTab().setText("Doctor"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return fragmentView;
    }

}
