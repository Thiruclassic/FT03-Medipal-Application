package iss.medipal.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import iss.medipal.R;


public class FriendFragment extends Fragment {

    private TextView tvEmmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_friend, container, false);
        ListView friendList = (ListView) fragmentView.findViewById(R.id.lv_friend_list);
        tvEmmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_friend);
        return fragmentView;

    }

    @Override
    public void onResume() {
        super.onResume();
        //to be implemented
    }
}
