package iss.medipal.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.adapters.AppointmentListAdapter;
import iss.medipal.util.AppHelper;


/**
 * Created by sreekumar
 * on 3/6/2017.
 */

public class AppointmentFragment extends Fragment implements AddAppointmentFragment.viewRefreshWhenAdded {


    private AppointmentDao appointmentDao;
    private ListView lv;
    FloatingActionButton addAppointment;
    FrameLayout innerLayout;
    private List<String> itemname;
    private AppointmentListAdapter appointmentListAdapter;
//    private ArrayList<Appointment> appointmentViewList;
    private ArrayList<Appointment> appointmentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment fragment = new AppointmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_appointment, container, false);
        lv = (ListView) fragmentView.findViewById(R.id.lvTodos);
        innerLayout = (FrameLayout) fragmentView.findViewById(R.id.add_appointment_frame);
        addAppointment = (FloatingActionButton) fragmentView.findViewById(R.id.addAppointment);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setListeners();

        //Change this to the singleton class
        appointmentDao = AppointmentDaoImpl.newInstance(getActivity());
        appointmentList = appointmentDao.getAllAppointments();

        if (itemname == null) {
            itemname = new ArrayList<>();
        }
        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList) {
                itemname.add(appointment.getLocation());
            }
        }
//        lv.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.todo_list_item, R.id.tvNote, itemname));
        setListAdapter(appointmentList);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    //todo add the adapter
    private void setListAdapter(List<Appointment> appointments) {
      /*  medicines = MediPalApplication.getPersonStore().getmPersonalBio().getMedicines();
        if(appointments!=null)
        {
            medicineListAdapter = new MedicineListAdapter(getContext(), medicines);
            medicineList.setAdapter(medicineListAdapter);
        }*/
        if (appointments != null) {
            appointmentListAdapter = new AppointmentListAdapter(getContext(), appointments);
            lv.setAdapter(appointmentListAdapter);
        }

    }

    //todo similar logic
    @Override
    public void onAppointmentAddedUiUpdate() {
        lv.setVisibility(View.VISIBLE);
        addAppointment.setVisibility(View.VISIBLE);
        innerLayout.setVisibility(View.GONE);

        try {

            appointmentList = appointmentDao.getAllAppointments();
         /*   medicines = MediPalApplication.getPersonStore()
                    .getmPersonalBio().getMedicines();*/
            if (!AppHelper.isListEmpty(appointmentList)) {
                if (appointmentListAdapter != null) {
                    appointmentListAdapter.setAppointments(appointmentList);
                } else {
                    appointmentListAdapter = new AppointmentListAdapter(getContext(), appointmentList);
                    lv.setAdapter(appointmentListAdapter);
                }
              /*  if(mMedAddCallback != null){
                    mMedAddCallback.onMedAdded();
                }*/

            }
        } catch (NullPointerException e) {
            System.out.printf(e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setListeners() {
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.add_appointment_frame, addAppointmentFragment).commit();
                lv.setVisibility(View.INVISIBLE);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.GONE);
            }
        });
      /*  FrameLayout.OnLayoutChangeListener AppLayoutChangeListener = new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                MainActivity activity = (MainActivity) v.getContext();
                if (activity.getmListener() == null) {

                    innerLayout.setVisibility(View.VISIBLE);
                    addAppointment.setVisibility(View.INVISIBLE);
                    Log.d("Fragment value", "hello");
                }
            }
        };*/

     /*   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.add_appointment_frame, addAppointmentFragment).commit();
                lv.setVisibility(View.INVISIBLE);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        });*/

        ListView.OnItemClickListener itemClickListener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance(appointmentList.get(position));
                FragmentManager manager = getChildFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.add_appointment_frame, addAppointmentFragment, Constants.ADD_FRAGMENT_PAGE).commit();
                lv.setVisibility(View.INVISIBLE);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        };
        lv.setOnItemClickListener(itemClickListener);

    }
}

