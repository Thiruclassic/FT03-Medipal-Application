package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;
import iss.medipal.model.homeMedicineModels.ConsumptionDayModel;
import iss.medipal.ui.adapters.ConsumtionDateAdapter;
import iss.medipal.ui.adapters.ConsumtionMedAdapter;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumptionMedFragment extends BaseFragment {

    private ExpandableListView mListView;
    private TextView mTvEmpty;

    private List<Consumption> mConsumtion;
    private List<Medicine> mMeds;
    private ConsumtionMedAdapter mAdapter;

    public static ConsumptionMedFragment newInstance() {
        ConsumptionMedFragment fragment = new ConsumptionMedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumption, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setExpandableListView(view);
    }

    private void setExpandableListView(View view){
        mListView = (ExpandableListView) view.findViewById(R.id.consumtion_list);
        mTvEmpty = (TextView) view.findViewById(R.id.no_items_tv);
        mMeds = MediPalApplication.getPersonStore().getmPersonalBio().getMedicines();
        mConsumtion = MediPalApplication.getPersonStore().getmConsumptions();
        if(!AppHelper.isListEmpty(mMeds)){
            mAdapter = new ConsumtionMedAdapter(getActivity(), mMeds, mConsumtion);
            mListView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter!=null)
            mTvEmpty.setVisibility(mAdapter.getGroupCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
