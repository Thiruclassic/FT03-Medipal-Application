package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Consumption;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.Medicine;
import iss.medipal.model.homeMedicineModels.MedDayModel;
import iss.medipal.model.homeMedicineModels.MedDoseModel;
import iss.medipal.ui.adapters.DoseListAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;

/**
 * Created by junaidramis on 19/3/17.
 */

public class OnedayDoseFragment extends BaseFragment implements DoseListAdapter.OnItemSelectedListener {

    private static final String ARGS_MED = "ARGS_MED";
    private static final String ARGS_MEDS = "ARGS_MEDS";
    private static final String ARGS_IS_FIRST = "ARGS_IS_FIRST";
    private static final String ARGS_IS_LAST = "ARGS_IS_LAST";
    private static final String ARGS_CAL = "ARGS_CAL";

    private TextView mMedTitle;
    private ListView mDoseList;
    private RadioButton mTookItButton;
    private ImageButton mPrevMedButton;
    private ImageButton mNextMedButton;

    private Calendar mCal;
    private Calendar mCurrentCal;
    private MedDayModel mMedicine;
    private ArrayList<Medicine> myMeds;
    private ArrayList<MedDoseModel> mDoseRecords;
    private Boolean isFirst, isLast;
    private OnMedicineArrowClickListener mArrowCallback;
    private DoseContainer mDoseContainer;
    private DoseListAdapter mAdapter;
    private int mSelectedPosition = -1;

    public static OnedayDoseFragment newInstance(MedDayModel med, List<Medicine> meds, Calendar cal,
                                                 Boolean isFront,
                                                 Boolean isLast) {
        OnedayDoseFragment fragment = new OnedayDoseFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MED, med);
        args.putParcelableArrayList(ARGS_MEDS, (ArrayList<Medicine>)meds);
        args.putSerializable(ARGS_CAL, cal);
        args.putBoolean(ARGS_IS_FIRST, isFront);
        args.putBoolean(ARGS_IS_LAST, isLast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoseContainer = DoseContainer.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oneday_dose, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        if(getArguments() != null && getArguments().getParcelable(ARGS_MED) != null){
            mMedicine = (MedDayModel) getArguments().getParcelable(ARGS_MED);
            if(!AppHelper.isListEmpty(getArguments().getParcelableArrayList(ARGS_MEDS))){
                myMeds = getArguments().getParcelableArrayList(ARGS_MEDS);
            }
            mCal = (Calendar) getArguments().getSerializable(ARGS_CAL);
            isFirst = getArguments().getBoolean(ARGS_IS_FIRST, false);
            isLast = getArguments().getBoolean(ARGS_IS_LAST, false);
        }
        setupDoseList();
        setUpButton(mSelectedPosition);
        setUpArrows();
        setListeners();
        setCalendar();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mArrowCallback = null;
    }

    @Override
    public void onItemSelected(int pos) {
        mSelectedPosition = pos;
        setUpButton(mSelectedPosition);
    }

    private void initialise(View view){
        mMedTitle = (TextView) view.findViewById(R.id.medNameText);
        mDoseList = (ListView) view.findViewById(R.id.dose_list_view);
        mTookItButton = (RadioButton) view.findViewById(R.id.tookButton);
        mPrevMedButton = (ImageButton) view.findViewById(R.id.prev_drug_button);
        mNextMedButton = (ImageButton) view.findViewById(R.id.next_drug_button);
    }

    private void setupDoseList() {
        if(mMedicine != null){
            mMedTitle.setText(mMedicine.getMedName());
        }
        mDoseRecords = mMedicine.getDoseRecordList();
        if(!AppHelper.isListEmpty(mDoseRecords)){
            mAdapter = new DoseListAdapter(getActivity(), this, mDoseRecords, myMeds);
            mDoseList.setAdapter(mAdapter);
        }
    }

    private void setUpButton(int mSelectedPosition){
        if(!AppHelper.isListEmpty(mDoseRecords)) {
            if(mSelectedPosition >= 0) {
                if(mDoseRecords.get(mSelectedPosition).getStatus() == Constants.TOOKIT_STATUS){
                    mTookItButton.setChecked(true);
                } else {
                    mTookItButton.setChecked(false);
                }
            } else {
                mTookItButton.setChecked(false);
            }
        }
    }

    /**
     *
     * hide arrow for first and last item in viewpager
     */
    private void setUpArrows(){
        if(isFirst){
            mPrevMedButton.setVisibility(View.INVISIBLE);
        }
        if(isLast){
            mNextMedButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setListeners(){
        mArrowCallback = ((OnMedicineArrowClickListener)getParentFragment());
        mNextMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrowCallback.onNextMedArrowClicked();
            }
        });
        mPrevMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrowCallback.onPreviousMedArrowClicked();
            }
        });
        mTookItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTookItClicked(mSelectedPosition);
            }
        });
    }

    private void setCalendar(){
        mCurrentCal = Calendar.getInstance();
    }

    public void onTookItClicked(int selectedPosition){
        if(!AppHelper.isListEmpty(mDoseRecords)) {
            if(selectedPosition >= 0){
                mDoseRecords.get(selectedPosition).setStatus(Constants.TOOKIT_STATUS);
                mAdapter.notifyDataSetChanged();
                Consumption consumption = new Consumption();
                Medicine medicine = mAdapter.getmCurrentMed();
                consumption.setMedicineId(mMedicine.getMedId());
                consumption.setQuantity(medicine.getDosage());
                mCurrentCal.setTime(medicine.getReminder().getStartTime());
                mCurrentCal.set(Calendar.YEAR, mCal.get(Calendar.YEAR));
                mCurrentCal.set(Calendar.MONTH, mCal.get(Calendar.MONTH));
                mCurrentCal.set(Calendar.DAY_OF_MONTH, mCal.get(Calendar.DAY_OF_MONTH));
                mCurrentCal.add(Calendar.HOUR, selectedPosition * medicine.getReminder().getInterval());
                consumption.setConsumedOn(mCurrentCal.getTime());
                MediPalApplication.getPersonStore().addConsumption(consumption);
                mDoseContainer.reloadConsumtionData();
            } else {
                DialogUtility.newMessageDialog(getContext(), getString(R.string.alert),
                        getString(R.string.select_dose)).show();
            }
            setUpButton(selectedPosition);
        }
    }

    public void onMissedit(int selectedPosition){
        if(!AppHelper.isListEmpty(mDoseRecords)) {
            if(selectedPosition >= 0){
                mDoseRecords.get(selectedPosition).setStatus(Constants.MISSEDIT_STATUS);
                mAdapter.notifyDataSetChanged();
                Consumption consumption = new Consumption();
                Medicine medicine = mAdapter.getmCurrentMed();
                consumption.setMedicineId(mMedicine.getMedId());
                consumption.setQuantity(medicine.getDosage());
                mCurrentCal.setTime(medicine.getReminder().getStartTime());
                mCurrentCal.set(Calendar.YEAR, mCal.get(Calendar.YEAR));
                mCurrentCal.set(Calendar.MONTH, mCal.get(Calendar.MONTH));
                mCurrentCal.set(Calendar.DAY_OF_MONTH, mCal.get(Calendar.DAY_OF_MONTH));
                mCurrentCal.add(Calendar.HOUR, selectedPosition * medicine.getReminder().getInterval());
                consumption.setConsumedOn(mCurrentCal.getTime());
                MediPalApplication.getPersonStore().deleteConsumption(consumption);
                mDoseContainer.reloadConsumtionData();
            } else {
                DialogUtility.newMessageDialog(getContext(), getString(R.string.alert),
                        getString(R.string.select_dose)).show();
            }
            setUpButton(selectedPosition);
        }
    }


    public interface OnMedicineArrowClickListener{
        void onNextMedArrowClicked();
        void onPreviousMedArrowClicked();
    }
}
