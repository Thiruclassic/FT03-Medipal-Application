package iss.medipal.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.ui.adapters.DoctorAdapter;
import iss.medipal.ui.adapters.FamilyAdapter;
import iss.medipal.util.AppHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    private TextView tvEmpty;
    private ListView familyMembersList;
    private FamilyAdapter familyAdapter;
    private ArrayList<InCaseofEmergencyContact> allContacts;
    private ArrayList<InCaseofEmergencyContact> familyContacts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View framgmentView = inflater.inflate(R.layout.fragment_family, container, false);
        return framgmentView;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        familyMembersList = (ListView) view.findViewById(R.id.lv_family_list);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_family);
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
        familyContacts = new ArrayList<>();
        if (!AppHelper.isListEmpty(allContacts)) {
            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.FAMILY_CONTACT)
                    familyContacts.add(contact);
            }
            if (!AppHelper.isListEmpty(allContacts)) {
                if (familyAdapter != null)
                    familyAdapter.setFamilyContacts(familyContacts);
                else {
                    familyAdapter = new FamilyAdapter(getContext(), familyContacts);
                    familyMembersList.setAdapter(familyAdapter);
                }
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if(familyAdapter!=null)
            tvEmpty.setVisibility(familyAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
