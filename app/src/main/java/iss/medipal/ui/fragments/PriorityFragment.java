package iss.medipal.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.ICEDao;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.ui.adapters.DoctorAdapter;
import iss.medipal.ui.adapters.PriorityAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriorityFragment extends Fragment {

    private TextView tvEmpty;
    private ListView priorityList;
    ICEDao iceDao;
    private List<String> contactNames;
    private PriorityAdapter priorityAdapter;
    private ArrayList<InCaseofEmergencyContact> allContacts;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_priority, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        priorityList = (ListView) view.findViewById(R.id.lv_doctor_list);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_doctor);
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
        Collections.sort(allContacts, new InCaseofEmergencyContact());

        if(allContacts != null){
            priorityAdapter = new PriorityAdapter(getContext(), allContacts);
            priorityList.setAdapter(priorityAdapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        tvEmpty.setVisibility(priorityAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

}
