package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iss.medipal.R;
import iss.medipal.ui.adapters.ReminderTabbedListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ViewTabbedReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTabbedReminderFragment extends Fragment {

    private ViewPager mViewPager;
    TabLayout tabLayout;


    public ViewTabbedReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment ViewTabbedReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTabbedReminderFragment newInstance() {
        ViewTabbedReminderFragment fragment = new ViewTabbedReminderFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_tabbed_reminder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager=(ViewPager)getActivity().findViewById(R.id.view_Pager);
        ReminderTabbedListAdapter reminderTabbedListAdapter =new ReminderTabbedListAdapter(getChildFragmentManager());

        mViewPager.setAdapter(reminderTabbedListAdapter);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.Remindertabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
