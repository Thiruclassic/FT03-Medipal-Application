package iss.medipal.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.model.Appointment;
import iss.medipal.model.Measurement;
import iss.medipal.model.Medicine;
import iss.medipal.ui.adapters.MedicineReminderListAdapter;
import iss.medipal.ui.adapters.RecyclerAdapter;
import iss.medipal.ui.adapters.ReminderRecyclerAdapter;
import iss.medipal.util.AppHelper;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class ViewAppointmentFragment extends Fragment {

    private RecyclerView recyclerView;

    public ViewAppointmentFragment() {


    }

    public static ViewAppointmentFragment newInstance() {
        ViewAppointmentFragment fragment = new ViewAppointmentFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_view_reminder_app, container, false);
        setUpRecyclerView(fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        reminderList=(ListView)view.findViewById(R.id.medicineReminderList);
//        mSwitch=(Switch)view.findViewById(R.id.medRemindItemSwitch);

        setList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setList() {

    }

    private void setUpRecyclerView(View fragmentView) {

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        ReminderRecyclerAdapter adapter = new ReminderRecyclerAdapter(getContext(), getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public List<Appointment> getData() {
        List<Appointment> data = new ArrayList<Appointment>();
        data = MediPalApplication.getPersonStore()
                .getmPersonalBio().getAppointments();

        return data;
    }

}
