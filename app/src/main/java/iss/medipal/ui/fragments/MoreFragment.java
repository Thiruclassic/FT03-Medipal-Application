package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import iss.medipal.R;
import iss.medipal.ui.activities.IceActivity;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.activities.UserProfileActivity;
import iss.medipal.ui.adapters.MoreAdapter;

/**
 * Created by junaidramis on 11/3/17.
 */

public class MoreFragment extends BaseFragment {

    private MoreAdapter mAdapter;
    private String[] mMoreItems;

    private ListView mMoreList;

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListView(view);
    }

    private void setListView(View view){
        mMoreItems = getResources().getStringArray(R.array.more_items);
        mMoreList = (ListView) view.findViewById(R.id.more_list_view);
        mAdapter = new MoreAdapter(getActivity(), new ArrayList<String>(Arrays.asList(mMoreItems)));
        mMoreList.setAdapter(mAdapter);
        mMoreList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        ((MainActivity)getActivity()).launchActivityWithoutFinishing(UserProfileActivity.class);

                    case 3:
                        ((MainActivity)getActivity()).launchActivityWithoutFinishing(IceActivity.class);
                }
            }
        });
    }
}
