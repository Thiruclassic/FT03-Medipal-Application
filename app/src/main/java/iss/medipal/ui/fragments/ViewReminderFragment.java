package iss.medipal.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.model.Medicine;
import iss.medipal.ui.adapters.MedicineReminderListAdapter;
import iss.medipal.util.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ViewReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewReminderFragment extends Fragment {

    private ListView reminderList;
    private BaseAdapter adapter;

    private Switch mSwitch;

    public ViewReminderFragment()  {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ViewReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewReminderFragment newInstance() {
        ViewReminderFragment fragment = new ViewReminderFragment();
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
        return inflater.inflate(R.layout.fragment_view_reminder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reminderList=(ListView)view.findViewById(R.id.medicineReminderList);
        mSwitch=(Switch)view.findViewById(R.id.medRemindItemSwitch);
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


    private void setList()
    {
        List<Medicine> medicines=medicines = MediPalApplication.getPersonStore()
                .getmPersonalBio().getMedicines();

        setReminderListAdapter(medicines);
        reminderList.setAdapter(adapter);


    }

    public void setReminderListAdapter(List<Medicine> medicines)
    {
        if(!AppHelper.isListEmpty(medicines)) {
            if(adapter==null)
            {
                adapter=new MedicineReminderListAdapter(getContext(),medicines);
            }
            else
            {
                ((MedicineReminderListAdapter)adapter).setMedicines(medicines);
            }
        }
    }


}
