package iss.medipal.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.ui.adapters.FamilyAdapter;
import iss.medipal.ui.adapters.FriendAdapter;
import iss.medipal.util.AppHelper;


public class FriendFragment extends Fragment {

    private TextView tvEmpty;
    private ListView friendList;
    private FriendAdapter friendAdapter;
    private ArrayList<InCaseofEmergencyContact> allContacts;
    private ArrayList<InCaseofEmergencyContact> friendContacts;

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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendList = (ListView) view.findViewById(R.id.lv_friend_list);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty_friend);
        allContacts = MediPalApplication.getPersonStore().getmPersonalBio().getContacts();  //store all contacts
        friendContacts = new ArrayList<>();
        Log.d("allContacts", Integer.toString(allContacts.size()));
        if (!AppHelper.isListEmpty(allContacts)) {
            for (InCaseofEmergencyContact contact : allContacts) {
                if (contact.getContactType() == Constants.FRIEND_CONTACT)
                    friendContacts.add(contact);
            }

            if (!AppHelper.isListEmpty(friendContacts)) {
                Log.d("friendContacts", Integer.toString(friendContacts.size()));
                if (friendAdapter != null)
                    friendAdapter.setFriendContacts(friendContacts);
                else {
                    friendAdapter = new FriendAdapter(getContext(), friendContacts);
                    friendList.setAdapter(friendAdapter);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(friendAdapter!=null)
        tvEmpty.setVisibility(friendAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
