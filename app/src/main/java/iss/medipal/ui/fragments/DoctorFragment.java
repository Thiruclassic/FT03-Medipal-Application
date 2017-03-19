package iss.medipal.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.R;
import iss.medipal.dao.ICEDao;
import iss.medipal.dao.impl.IceDaoImpl;
import iss.medipal.ui.adapters.DoctorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

    private TextView tvEmpty;
    private ListView doctorList;
    ICEDao iceDao;
    private List<String> contactNames;
    private DoctorAdapter doctorAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_doctor, container, false);
        doctorList = (ListView) fragmentView.findViewById(R.id.lv_doctor_list);
        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_doctor);
        doctorAdapter = new DoctorAdapter(getActivity());
        doctorList.setAdapter(doctorAdapter);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iceDao = new IceDaoImpl(getContext());
        iceDao.getContactsbyType(3);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvEmpty.setVisibility(doctorAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
