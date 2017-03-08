package iss.medipal.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iss.medipal.R;
import iss.medipal.ui.adapters.BaseSpinnerAdapter;

/**
 * Created by junaidramis on 8/3/17.
 */
public class UserProfileEditFragment extends BaseFragment{

    private List<String> mSpinnerItems;

    private AppCompatSpinner mBloodSpinner;

    public static UserProfileEditFragment newInstance() {
        UserProfileEditFragment fragment = new UserProfileEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseView(view);
        setSpinner();
    }

    private void initialiseView(View view){
        mBloodSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_blood_list);
    }

    private void setSpinner(){
        mSpinnerItems = new ArrayList<>();
        mSpinnerItems.add("-");
        mSpinnerItems.addAll(Arrays.asList(getResources().getStringArray(R.array.blood_type_items)));
        BaseSpinnerAdapter adapter = new BaseSpinnerAdapter(getActivity(), R.layout.dropdown_header, mSpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBloodSpinner.setAdapter(adapter);
    }
}
