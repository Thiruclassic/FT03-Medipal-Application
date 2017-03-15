package iss.medipal.ui.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.ui.activities.MainActivity;


/**
 * Created by sreekumar
 * on 3/6/2017.
 */

public class AppointmentFragment extends Fragment {


    AppointmentDao appointmentDao;
    ListView lv;
    FloatingActionButton addAppointment;
    FrameLayout innerLayout;
    List<String> itemname;

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
        setListeners();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        appointmentDao = AppointmentDaoImpl.newInstance(getActivity());
        List<Appointment> appointmentList = appointmentDao.getAllAppointments();

        if(itemname==null)
        {
            itemname=new ArrayList<>();
        }
        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList) {
                itemname.add(appointment.getLocation());
            }
        }
          lv.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.todo_list_item, R.id.tvNote, itemname));
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void setListeners() {
      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
             /*   Intent intent = new Intent(getActivity(), TodoFragment.class);
                String content = (String) lv.getItemAtPosition(pos);
                intent.putExtra("Content", content);
                startActivity(intent);*/

                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
//                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment,Constants.ADD_FRAGMENT_PAGE).commit();
                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment).commit();
                lv.setVisibility(View.INVISIBLE);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        });

          addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
//                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment,Constants.ADD_FRAGMENT_PAGE).commit();
                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment).commit();
                lv.setVisibility(View.INVISIBLE);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        });
        final View.OnClickListener addAppEvent=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                innerLayout.setVisibility(View.INVISIBLE);
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();

//                FragmentManager manager=getChildFragmentManager();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
//                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment,Constants.ADD_FRAGMENT_PAGE).commit();
                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment).commit();
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        };

        FrameLayout.OnLayoutChangeListener AppLayoutChangeListener=new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                MainActivity activity=(MainActivity) v.getContext();
                if(activity.getmListener()==null)
                {

                    innerLayout.setVisibility(View.VISIBLE);
                    addAppointment.setVisibility(View.INVISIBLE);
                    Log.d("Fragment value","hello");
                }
            }
        };
        ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getContext(), "position" + position + "id" + id, Toast.LENGTH_SHORT).show();
                //Pass the clicked element reference
                AddAppointmentFragment addAppointmentFragment = AddAppointmentFragment.newInstance();
//                FragmentManager manager = getChildFragmentManager();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.add_appointment_frame, addAppointmentFragment, Constants.ADD_FRAGMENT_PAGE).commit();
                transaction.replace(R.id.add_appointment_frame,addAppointmentFragment);
                innerLayout.setVisibility(View.VISIBLE);
                addAppointment.setVisibility(View.INVISIBLE);
            }
        };
//      lv.setOnItemClickListener(itemClickListener);
//        innerLayout.addOnLayoutChangeListener(AppLayoutChangeListener);
//      addAppointment.setOnClickListener(addAppEvent);

    }
}
