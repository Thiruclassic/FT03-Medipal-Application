package iss.medipal.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.model.Medicine;
import iss.medipal.ui.adapters.MedicineListAdapter;
import iss.medipal.ui.interfaces.OnTaskCompleted;
import iss.medipal.util.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMedicineFragment extends Fragment implements AddMedicineFragment.ViewMedInterface,
        MedicineListAdapter.onMedicineDeletedInterface, OnTaskCompleted{

    private FloatingActionButton addMedicineButton;
    private ListView medicineList;
    private FrameLayout innerLayout;
    private MedicineListAdapter medicineListAdapter;
    private List<Medicine> medicines;
    private List<String> medicineNames;
    private MedicineDao medicineDao;
    private HomeInterface mMedAddCallback;
    private TextView tvEmpty;

    public ViewMedicineFragment() {
        // Required empty public constructor
    }

    public static ViewMedicineFragment newInstance() {
        ViewMedicineFragment fragment = new ViewMedicineFragment();
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
        View view=inflater.inflate(R.layout.fragment_view_medicine, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMedAddCallback = (HomeInterface)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(medicineListAdapter!=null) {
            tvEmpty.setVisibility(medicineListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseUI(view);
        setListeners();
        setList();
    }

    @Override
    public void onMedAddedUiUpdate(boolean isUpdated) {
        try {
            addMedicineButton.setVisibility(View.VISIBLE);
            innerLayout.setVisibility(View.GONE);
            if(isUpdated){
            medicines = MediPalApplication.getPersonStore()
                    .getmPersonalBio().getMedicines();
            if(!AppHelper.isListEmpty(medicines)) {
                if (medicineListAdapter != null) {
                    medicineListAdapter.setMedicines(medicines);
                } else {
                    medicineListAdapter = new MedicineListAdapter(getContext(), medicines, this);
                    medicineList.setAdapter(medicineListAdapter);
                }
                doCallback();
            }

            }
        } catch (NullPointerException e){
            System.out.printf(e.getMessage());
        }
    }

    @Override
    public void onMedDeleted(Medicine medicine) {
        MediPalApplication.getPersonStore().deleteMedicine(medicine, this);
    }

    @Override
    public void onTaskCompleted() {
        doCallback();
    }

    private void initialiseUI(View view){
        addMedicineButton=(FloatingActionButton)view.findViewById(R.id.addMedicine);
        medicineList=(ListView)view.findViewById(R.id.medicineList);
        innerLayout=(FrameLayout)view.findViewById(R.id.add_medicine_frame);
        tvEmpty=(TextView)view.findViewById(R.id.tv_empty_medicine);

    }

    private void setList(){
        medicines = MediPalApplication.getPersonStore().getmPersonalBio().getMedicines();
        if(medicines!=null)
        {
            medicineListAdapter = new MedicineListAdapter(getContext(), medicines, this);
            medicineList.setAdapter(medicineListAdapter);
        }

    }

    public void doCallback(){
        if(mMedAddCallback != null){
            mMedAddCallback.onMedAdded();
        }
    }

    public void setListeners() {
        final View.OnClickListener addMedicineEvent=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicineFragment addMedicineTab=AddMedicineFragment.newInstance();
                FragmentManager manager=getChildFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.add_medicine_frame,addMedicineTab,Constants.ADD_MEDICINE_PAGE).commit();
                innerLayout.setVisibility(View.VISIBLE);
                addMedicineButton.setVisibility(View.GONE);
            }
        };
        ListView.OnItemClickListener itemClickListener=new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddMedicineFragment addMedicineTab = AddMedicineFragment.newInstance(medicines.get(position));
                FragmentManager manager=getChildFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.add_medicine_frame,addMedicineTab,Constants.ADD_MEDICINE_PAGE).commit();
                innerLayout.setVisibility(View.VISIBLE);
                addMedicineButton.setVisibility(View.INVISIBLE);
            }
        };
        medicineList.setOnItemClickListener(itemClickListener);
        addMedicineButton.setOnClickListener(addMedicineEvent);
    }

    public interface HomeInterface {
        void onMedAdded();
    }
}
