package iss.medipal.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
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
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.model.Medicine;
import iss.medipal.ui.activities.Add_ICE;
import iss.medipal.ui.activities.IceActivity;
import iss.medipal.ui.adapters.FamilyAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    private TextView tvEmpty;
    private ListView familyMembersList;
    private FamilyAdapter familyAdapter;
    private List<InCaseofEmergencyContact> allContacts;
    private List<InCaseofEmergencyContact> familyContacts;
    private static int pos;

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
        setList();

    }

    public void setList()
    {
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
        familyContacts = new ArrayList<>();
        if (!AppHelper.isListEmpty(allContacts)) {
            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.FAMILY_CONTACT)
                if (contact.getContactType() == Constants.FAMILY_CONTACT)
                    familyContacts.add(contact);
            }
            if (!AppHelper.isListEmpty(allContacts)) {
                if (familyAdapter != null)
                    familyAdapter.setFamilyContacts(familyContacts);
                else {
                    familyAdapter = new FamilyAdapter(getContext(), familyContacts);

                }
            }
            familyMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    showPopupMenu(view,position);
                }
            });
            familyMembersList.setAdapter(familyAdapter);
        }
    }

    public void setClickListener(final int position, View view)
    {
        final DialogInterface.OnClickListener yesClick=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InCaseofEmergencyContact contact=familyContacts.remove(position);
                familyAdapter.setFamilyContacts(familyContacts);
                MediPalApplication.getPersonStore().deleteContact(contact);
            }
        };

                DialogUtility.newMessageDialogWIthYesNo(getContext(), getContext().getString(R.string.warning),
                        "Are you sure you want to delete "+familyContacts.get(position).getContactName(),yesClick).show();

    }

    /*private AlertDialog AskOption(final InCaseofEmergencyContact familyContact)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this.getContext())
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete_contact)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        familyContacts.remove(familyContact);
                        familyAdapter.setFamilyContacts(familyContacts);
                        MediPalApplication.getPersonStore().deleteContact(familyContact);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }*/


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
                        Toast.makeText(getContext(), "calling " + familyContacts.get(pos).getContactName(),   Toast.LENGTH_SHORT).show();
                        String call_number = "tel:" + familyContacts.get(pos).getContactNo();

                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(call_number));
                        startActivity(callIntent);
                        return true;
                    case R.id.sms_contact:
                        Toast.makeText(getContext(), "texting " + familyContacts.get(pos).getContactName(), Toast.LENGTH_SHORT).show();
                        String text_number = Long.toString(familyContacts.get(pos).getContactNo());
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", text_number, null)));
                        //SmsManager smsManager = SmsManager.getDefault();
                        //smsManager.sendTextMessage(text_number,null,text_body,null,null);
                        return true;

                    case R.id.edit_contact:
                        Intent intent = new Intent(getActivity(), Add_ICE.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable(DBConstants.TABLE_ICE,familyContacts.get(position));
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


    @Override
    public void onResume() {
        super.onResume();
        setList();
        if(familyAdapter!=null)
            tvEmpty.setVisibility(familyAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
