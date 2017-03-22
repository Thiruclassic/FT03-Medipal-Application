package iss.medipal.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.ui.adapters.DoctorAdapter;
import iss.medipal.util.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

    private TextView tvEmpty;
    private ListView doctorList;
    ICEDao iceDao;
    private List<String> contactNames;
    private DoctorAdapter doctorAdapter;
    private ArrayList<InCaseofEmergencyContact> allContacts;
    private ArrayList<InCaseofEmergencyContact> doctorContacts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_doctor, container, false);
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctorList = (ListView) view.findViewById(R.id.lv_doctor_list);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_doctor);

        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
        if (!AppHelper.isListEmpty(allContacts)) {
            doctorContacts = new ArrayList<>();
            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.DOCTOR_CONTACT)
                    doctorContacts.add(contact);
            }
            if (!AppHelper.isListEmpty(doctorContacts)) {
                if (doctorAdapter != null)
                    doctorAdapter.setDoctorContacts(doctorContacts);
                else {
                    doctorAdapter = new DoctorAdapter(getContext(), doctorContacts);
                    doctorList.setAdapter(doctorAdapter);
                }
            }
            //iceDao = new IceDaoImpl(getContext());
            //iceDao.getContactsbyType(3);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(doctorAdapter!=null)
            tvEmpty.setVisibility(doctorAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
