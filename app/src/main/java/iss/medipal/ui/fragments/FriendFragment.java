package iss.medipal.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import iss.medipal.ui.activities.Add_ICE;
import iss.medipal.ui.adapters.FriendAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;


public class FriendFragment extends Fragment {

    private TextView tvEmpty;
    private ListView friendList;
    private FriendAdapter friendAdapter;
    private List<InCaseofEmergencyContact> allContacts;
    private List<InCaseofEmergencyContact> friendContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_friend, container, false);
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
        Log.d("view state","######Restored");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendList = (ListView) view.findViewById(R.id.lv_friend_list);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_friend);

    }
    @Override
    public void onResume() {
        super.onResume();
        setFriendList();
        if(friendAdapter!=null)
        tvEmpty.setVisibility(friendAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);


    }

    public void setFriendList()
    {
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
            friendContacts = new ArrayList<>();
        if (!AppHelper.isListEmpty(allContacts)) {
            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.FRIEND_CONTACT) {
                    friendContacts.add(contact);
                    Log.d("friendContacts Name",contact.getContactName());
                }

            }
        }
                Log.d("friendContacts", Integer.toString(friendContacts.size()));
                if (friendAdapter != null)
                    friendAdapter.setFriendContacts(friendContacts);
                else {
                    friendAdapter = new FriendAdapter(getContext(), friendContacts);
                }
        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,position);
            }
        });

        friendList.setAdapter(friendAdapter);
    }

    public void setClickListener(final int position, View view)
    {
        final DialogInterface.OnClickListener yesClick=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InCaseofEmergencyContact contact=friendContacts.remove(position);
                friendAdapter.setFriendContacts(friendContacts);
                MediPalApplication.getPersonStore().deleteContact(contact);
            }
        };

        DialogUtility.newMessageDialogWIthYesNo(getContext(), getContext().getString(R.string.warning),
                "Are you sure you want to delete "+friendContacts.get(position).getContactName(),yesClick).show();

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
                        Toast.makeText(getContext(), "calling " + friendContacts.get(position).getContactName(),   Toast.LENGTH_SHORT).show();
                        String number = "tel:" + friendContacts.get(position).getContactNo();

                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                        startActivity(callIntent);
                        return true;
                    case R.id.edit_contact:
                        Intent intent = new Intent(getActivity(), Add_ICE.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable(DBConstants.TABLE_ICE,friendContacts.get(position));
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
