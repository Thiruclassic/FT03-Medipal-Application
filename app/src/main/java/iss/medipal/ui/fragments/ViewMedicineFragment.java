package iss.medipal.ui.fragments;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.ui.activities.BaseActivity;
import iss.medipal.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMedicineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button addMedicineButton;

    ListView medicineList;

    FrameLayout innerLayout;

    ArrayAdapter<String> medicineListAdapter;

    List<String> medicineNames;

    MedicineDao medicineDao;



    public ViewMedicineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMedicineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMedicineFragment newInstance(String param1, String param2) {
        ViewMedicineFragment fragment = new ViewMedicineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView=inflater.inflate(R.layout.fragment_view_medicine, container, false);
        addMedicineButton=(Button)thisView.findViewById(R.id.addMedicine);
        medicineList=(ListView)thisView.findViewById(R.id.medicineList);
        innerLayout=(FrameLayout)thisView.findViewById(R.id.add_medicine_frame);
        setListeners();

        return thisView;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medicineDao= MedicineDaoImpl.newInstance();
        medicineNames=medicineDao.getAllMedicines();
        if(medicineNames==null)
        {
            medicineNames=new ArrayList<>();
        }
        medicineListAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,medicineNames);

        medicineList.setAdapter(medicineListAdapter);




    }

    public void setListeners()
    {
        final View.OnClickListener addMedicineEvent=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicineFragment addMedicineTab=AddMedicineFragment.newInstance(0);

                FragmentManager manager=getChildFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.add_medicine_frame,addMedicineTab,Constants.ADD_MEDICINE_PAGE).commit();
                innerLayout.setVisibility(View.VISIBLE);
                addMedicineButton.setVisibility(View.INVISIBLE);
            }
        };


        FrameLayout.OnLayoutChangeListener layoutChangeListener=new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                MainActivity activity=(MainActivity) v.getContext();
                if(activity.getmListener()==null)
                {
                   addMedicineButton.setVisibility(View.VISIBLE);
                    innerLayout.setVisibility(View.INVISIBLE);

                    Log.d("Fragment value","hello");
                }
            }
        };

        ListView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(),  "position"+position+"id"+id, Toast.LENGTH_SHORT).show();
                AddMedicineFragment addMedicineTab=AddMedicineFragment.newInstance(position+1);

                FragmentManager manager=getChildFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.add_medicine_frame,addMedicineTab,Constants.ADD_MEDICINE_PAGE).commit();

                innerLayout.setVisibility(View.VISIBLE);
                addMedicineButton.setVisibility(View.INVISIBLE);
            }
        };


         medicineList.setOnItemClickListener(itemClickListener);
         innerLayout.addOnLayoutChangeListener(layoutChangeListener);
         addMedicineButton.setOnClickListener(addMedicineEvent);
    }

}
