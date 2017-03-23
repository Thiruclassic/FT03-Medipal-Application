package iss.medipal.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.PersonStore;
import iss.medipal.model.PersonalBio;
import iss.medipal.ui.activities.HealthBioActivity;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.activities.UserProfileActivity;
import iss.medipal.ui.adapters.BaseSpinnerAdapter;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */
public class UserProfileEditFragment extends BaseFragment implements UserProfileActivity.EditClickListener{

    private static final String ARGS_EDIT = "ARGS_EDIT";

    private List<String> mSpinnerItems;
    private SimpleDateFormat mTimeFormatter;
    private DatePickerDialog mDatePickerDialog;
    private PersonStore mPersonStore;
    private Activity mActivity;
    private boolean isEdit;

    private AppCompatSpinner mBloodSpinner;
    private AppCompatEditText mNameEditText;
    private AppCompatEditText mIdEditText;
    private AppCompatEditText mDobEditText;
    private AppCompatEditText mHeightEditText;
    private AppCompatEditText mBuildingEditText;
    private AppCompatEditText mLocationEditText;
    private AppCompatEditText mStreetEditText;
    private AppCompatEditText mLevelEditText;
    private AppCompatEditText mUnitEditText;
    private AppCompatEditText mPostalCodeEditText;
    private Button mSubmitButton;


    public static UserProfileEditFragment newInstance(Boolean isEdit) {
        UserProfileEditFragment fragment = new UserProfileEditFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARGS_EDIT, isEdit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimeFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        if(getArguments() != null) {
            isEdit = getArguments().getBoolean(ARGS_EDIT, true);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mActivity = (UserProfileActivity) context;
        ((UserProfileActivity)mActivity).setmEditClickListener(this);
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
        ((UserProfileActivity)getActivity()).setToolbar(isEdit);
        mPersonStore = ((MediPalApplication)getActivity()
                .getApplicationContext()).getPersonStore();
        if(isEdit) {
            setListeners();
        } else {
            setProfileViewMode(mPersonStore.getmPersonalBio());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((UserProfileActivity)mActivity).setmEditClickListener(null);
        mActivity = null;
    }

    @Override
    public void onEditClicked() {
        setViewsEnabled(true);
    }

    @Override
    public void onSaveClicked() {
        if(isValid()) {
            setViewsEnabled(false);
            PersonalBio personalBio = new PersonalBio();
            personalBio.setId(mPersonStore.getmPersonalBio().getId());
            personalBio.setName(mNameEditText.getText().toString());
            personalBio.setIdNo(mIdEditText.getText().toString());
            personalBio.setDob(mDobEditText.getText().toString());
            personalBio.setHeight(Integer.valueOf(mHeightEditText.getText().toString()));
            personalBio.setBloodType((String) mBloodSpinner.getSelectedItem());
            personalBio.setAddress(mBuildingEditText.getText().toString() + "," +
                    mLocationEditText.getText().toString() + "," +
                    mStreetEditText.getText().toString() + "," +
                    mLevelEditText.getText().toString() + "-" +
                    mUnitEditText.getText().toString());
            personalBio.setPostalCode(mPostalCodeEditText.getText().toString());
            mPersonStore.editPersonalBio(personalBio);
        }
    }

    private void initialiseView(View view){
        mBloodSpinner = (AppCompatSpinner) view.findViewById(R.id.spinner_blood_list);
        mNameEditText = (AppCompatEditText) view.findViewById(R.id.name_edit);
        mIdEditText = (AppCompatEditText) view.findViewById(R.id.id_edit);
        mDobEditText = (AppCompatEditText) view.findViewById(R.id.dob_edit);
        mHeightEditText = (AppCompatEditText) view.findViewById(R.id.height_edit);
        mBuildingEditText = (AppCompatEditText) view.findViewById(R.id.building_edit);
        mLocationEditText = (AppCompatEditText) view.findViewById(R.id.location_edit);
        mStreetEditText = (AppCompatEditText) view.findViewById(R.id.streetname_edit);
        mLevelEditText = (AppCompatEditText) view.findViewById(R.id.level_edit);
        mUnitEditText = (AppCompatEditText) view.findViewById(R.id.unit_edit);
        mPostalCodeEditText = (AppCompatEditText) view.findViewById(R.id.postal_edit);
        mSubmitButton = (Button) view.findViewById(R.id.submit_btn);
    }

    private void setSpinner(){
        mSpinnerItems = new ArrayList<>();
        mSpinnerItems.add("-");
        mSpinnerItems.addAll(Arrays.asList(getResources().getStringArray(R.array.blood_type_items)));
        BaseSpinnerAdapter adapter = new BaseSpinnerAdapter(getActivity(), R.layout.dropdown_header, mSpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBloodSpinner.setAdapter(adapter);
    }

    private DatePickerDialog getDialog(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDobEditText.setText(mTimeFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return dialog;
    }

    private void setListeners(){
        mSubmitButton.setOnClickListener(mSubmitListener);
        mDobEditText.setOnClickListener(mTimeClickListener);
        mDobEditText.setOnFocusChangeListener(mTimeFocusListener);
    }

    private boolean isValid(){
        if(TextUtils.isEmpty(mNameEditText.getText()) ||
                TextUtils.isEmpty(mIdEditText.getText()) ||
                TextUtils.isEmpty(mHeightEditText.getText()) ||
                TextUtils.isEmpty(mBuildingEditText.getText()) ||
                TextUtils.isEmpty(mLocationEditText.getText()) ||
                TextUtils.isEmpty(mStreetEditText.getText()) ||
                TextUtils.isEmpty(mLevelEditText.getText()) ||
                TextUtils.isEmpty(mUnitEditText.getText()) ||
                TextUtils.isEmpty(mPostalCodeEditText.getText()) ||
                TextUtils.isEmpty(mDobEditText.getText()) ||
                mBloodSpinner.getSelectedItemPosition() <= 0) {
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    getString(R.string.enter_all_details_text)).show();
            return false;
        }
        return true;
    }

    private void setProfileViewMode(PersonalBio personalBio){
        mNameEditText.setText(personalBio.getName());
        mIdEditText.setText(personalBio.getIdNo());
        mDobEditText.setText(personalBio.getDob());
        mHeightEditText.setText(String.valueOf(personalBio.getHeight()));
        String[] address = personalBio.getAddress().split(",");
        mBuildingEditText.setText(address[0]);
        mLocationEditText.setText(address[1]);
        mStreetEditText.setText(address[2]);
        String[] internalAddress = address[3].split("-");
        mLevelEditText.setText(internalAddress[0]);
        mUnitEditText.setText(internalAddress[1]);
        mPostalCodeEditText.setText(personalBio.getPostalCode());
        mBloodSpinner.setSelection(getSpinnerSelectedIndex(mBloodSpinner, personalBio.getBloodType()));
        mSubmitButton.setVisibility(View.GONE);
        setViewsEnabled(false);
    }

    private void setViewsEnabled(boolean isEnabled){
        mNameEditText.setEnabled(isEnabled);
        mIdEditText.setEnabled(isEnabled);
        mDobEditText.setEnabled(isEnabled);
        mHeightEditText.setEnabled(isEnabled);
        mBuildingEditText.setEnabled(isEnabled);
        mLocationEditText.setEnabled(isEnabled);
        mStreetEditText.setEnabled(isEnabled);
        mLevelEditText.setEnabled(isEnabled);
        mUnitEditText.setEnabled(isEnabled);
        mPostalCodeEditText.setEnabled(isEnabled);
        mBloodSpinner.setEnabled(isEnabled);
        if(isEnabled){
            mDobEditText.setOnClickListener(mTimeClickListener);
            mDobEditText.setOnFocusChangeListener(mTimeFocusListener);
        } else {
            mDobEditText.setOnClickListener(null);
            mDobEditText.setOnFocusChangeListener(null);
        }
    }

    private int getSpinnerSelectedIndex(Spinner spinner, String string)
    {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)){
                index = i;
                break;
            }
        }
        return index;
    }

    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isValid()){
                PersonalBio personalBio = new PersonalBio();
                personalBio.setName(mNameEditText.getText().toString());
                personalBio.setIdNo(mIdEditText.getText().toString());
                personalBio.setDob(mDobEditText.getText().toString());
                personalBio.setHeight(Integer.valueOf(mHeightEditText.getText().toString()));
                personalBio.setBloodType((String) mBloodSpinner.getSelectedItem());
                personalBio.setAddress(mBuildingEditText.getText().toString() + "," +
                        mLocationEditText.getText().toString() + "," +
                        mStreetEditText.getText().toString() + "," +
                        mLevelEditText.getText().toString() + "-" +
                        mUnitEditText.getText().toString());
                personalBio.setPostalCode(mPostalCodeEditText.getText().toString());
                mPersonStore.addPersonBio(personalBio);
              //  SharedPreferenceManager.setAppLaunchStatus(getActivity(), false);
                ((UserProfileActivity)getActivity()).launchActivity(HealthBioActivity.class);
            }
        }
    };

    private View.OnFocusChangeListener mTimeFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                mDatePickerDialog = getDialog();
                mDatePickerDialog.show();
            }
        }
    };

    private View.OnClickListener mTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDatePickerDialog = getDialog();
            mDatePickerDialog.show();
        }
    };
}
