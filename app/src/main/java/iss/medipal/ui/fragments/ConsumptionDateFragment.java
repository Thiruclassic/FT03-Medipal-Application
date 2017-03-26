package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import iss.medipal.R;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.homeMedicineModels.ConsumptionDayModel;
import iss.medipal.ui.adapters.ConsumtionDateAdapter;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumptionDateFragment extends BaseFragment {

    private ExpandableListView mListView;

    private DoseContainer mDoseContainer;
    private ConsumtionDateAdapter mAdapter;
    private List<ConsumptionDayModel> mMedDayModels;

    public static ConsumptionDateFragment newInstance() {
        ConsumptionDateFragment fragment = new ConsumptionDateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoseContainer = DoseContainer.getInstance(getActivity());
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
        mMedDayModels = mDoseContainer.getmConsumtionDayModel();
        if(!AppHelper.isListEmpty(mMedDayModels)){
            mAdapter = new ConsumtionDateAdapter(getActivity(), mMedDayModels);
            mListView.setAdapter(mAdapter);
        }
    }
}
