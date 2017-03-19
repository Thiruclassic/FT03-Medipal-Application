package iss.medipal.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import iss.medipal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

private TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View framgmentView = inflater.inflate(R.layout.fragment_family, container, false);
        ListView familyList = (ListView) framgmentView.findViewById(R.id.lv_family_list);
        tvEmpty = (TextView) framgmentView.findViewById(R.id.tv_empty_family);

        return framgmentView;

    }

}
