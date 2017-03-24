package iss.medipal.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.ICEDao;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.ui.activities.Add_ICE;
import iss.medipal.ui.adapters.DoctorAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

    private TextView tvEmpty;
    private ListView doctorList;
    ICEDao iceDao;
    private List<String> contactNames;
    private DoctorAdapter doctorAdapter;
    private List<InCaseofEmergencyContact> allContacts;
    private List<InCaseofEmergencyContact> doctorContacts;



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
    }

    @Override
    public void onResume() {
        super.onResume();
        setDoctorList();
        if(doctorAdapter!=null)
            tvEmpty.setVisibility(doctorAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public void setDoctorList() {
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts

        doctorContacts = new ArrayList<>();
        if (!AppHelper.isListEmpty(allContacts)) {

            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.DOCTOR_CONTACT)
                    doctorContacts.add(contact);
            }
            if (!AppHelper.isListEmpty(doctorContacts)) {
                if (doctorAdapter != null)
                    doctorAdapter.setDoctorContacts(doctorContacts);
                else {
                    doctorAdapter = new DoctorAdapter(getContext(), doctorContacts);
                }
            }

            doctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showPopupMenu(view,position);
                }
            });

            doctorList.setAdapter(doctorAdapter);
        }
    }

    public void setClickListener(final int position, View view)
    {
        final DialogInterface.OnClickListener yesClick=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InCaseofEmergencyContact contact=doctorContacts.remove(position);
                doctorAdapter.setDoctorContacts(doctorContacts);
                MediPalApplication.getPersonStore().deleteContact(contact);
            }
        };

        DialogUtility.newMessageDialogWIthYesNo(getContext(), getContext().getString(R.string.warning),
                "Are you sure you want to delete "+doctorContacts.get(position).getContactName(),yesClick).show();

    }

    public void showPopupMenu(final View v, final int position){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.contact_options_menu,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.call_contact:
                        Toast.makeText(getContext(), "calling " + doctorContacts.get(position).getContactName(),   Toast.LENGTH_SHORT).show();
                        String number = "tel:" + doctorContacts.get(position).getContactNo();

                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                        startActivity(callIntent);
                        return true;
                    case R.id.sms_contact:
                        Toast.makeText(getContext(), "texting " + doctorContacts.get(position).getContactName(), Toast.LENGTH_SHORT).show();
                        String text_number = Long.toString(doctorContacts.get(position).getContactNo());
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", text_number, null)));
                        return true;
                    case R.id.edit_contact:
                        Intent intent = new Intent(getActivity(), Add_ICE.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable(DBConstants.TABLE_ICE,doctorContacts.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return true;
                    case R.id.delete_contact:
                        setClickListener(position,v);
                        return true;
                    default: return false;
                }
            }
        });

    }

}
