package iss.medipal.ui.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.CategoryDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Category;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.receivers.AlarmReceiver;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.adapters.BaseSpinnerAdapter;
import iss.medipal.ui.adapters.CategorySpinnerAdapter;
import iss.medipal.ui.interfaces.CustomBackPressedListener;
import iss.medipal.util.AppHelper;
import iss.medipal.util.DialogUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMedicineFragment extends BaseTimeFragment implements CustomBackPressedListener{

    private static final String ARGS_MED = "ARGS_MED";

    private AppCompatEditText mMedicineNameEditText;
    private AppCompatEditText mDescriptionEditText;
    private AppCompatEditText mTotalQuantityEditText;
    private AppCompatEditText mIssueDateEditText;
    private AppCompatEditText mMonthsToExpireEditText;
    private AppCompatEditText mPillsBeforeRefillEditText;
    private Button mSaveMedicineButton;
    private Button mAddDosageTimeButton;
    private Button mAddRefillReminderTime;
    private AppCompatSpinner mCategorySpinner;
    private AppCompatSpinner mDosageSpinner;
    private AppCompatSpinner mFrequencySpinner;
    private AppCompatSpinner mIntervalSpinner;
    private Switch mRemindSwitch;
    private Switch mRefillReminderSwitch;

    private MedicineDao medicineDao;
    private ReminderDao reminderDao;
    private CategoryDao categoryDao;

    private Medicine mMedicine;
    private Reminder mReminder;
    private boolean isEditMedicine;

    private String mReminderStartTime;
    private List<String> mSpinnerItems;


    private CustomBackPressedListener mListener;

    public AddMedicineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddMedicineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMedicineFragment newInstance() {
        AddMedicineFragment fragment = new AddMedicineFragment();
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddMedicineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMedicineFragment newInstance(Medicine medicine) {
        AddMedicineFragment fragment = new AddMedicineFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MED, medicine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView=inflater.inflate(R.layout.fragment_add_update_medicine, container, false);
        return thisView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initialiseViews(view);
        setSpinners();
        if(getArguments() != null && getArguments().getParcelable(ARGS_MED) != null){
            mMedicine = (Medicine) getArguments().getParcelable(ARGS_MED);
        }
        if(mMedicine !=null) {
            updateMedicineDetails();
            updateReminderDetails();
            mSaveMedicineButton.setText("Modify Medicine");
            isEditMedicine = true;
        } else {
            mMedicine = new Medicine();
            isEditMedicine = false;
        }
        setListeners();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).setmListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void doBack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.detach(this).commit();
        ((MainActivity)getActivity()).setmListener(null);
    }

    @Override
    public void onTimeSelected(int hourOfDay, int minute) {
        String doseTime = AppHelper.convert24TimeTo12String(hourOfDay, minute);
        mReminderStartTime = AppHelper.convertTimeFormat(doseTime, Constants.TIME_FORMAT_STORAGE,
                Constants.TIME_12_HOUR_FORMAT);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        if(mReminder == null){
            mReminder  = new Reminder();
        }
        mReminder.setStartTime(calendar.getTime());
        mAddDosageTimeButton.setText(mReminderStartTime);
    }

    private void setSpinners(){
        List<Category> categories = MediPalApplication.getPersonStore().getCategory();
        CategorySpinnerAdapter categoryAdapter = new CategorySpinnerAdapter(getContext(),
                R.layout.dropdown_header ,categories);
        mCategorySpinner.setAdapter(categoryAdapter);
        mSpinnerItems = new ArrayList<>();
        mSpinnerItems.addAll(Arrays.asList(getResources().getStringArray(R.array.dosage_quantity_items)));
        BaseSpinnerAdapter adapter = new BaseSpinnerAdapter(getActivity(), R.layout.dropdown_header, mSpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDosageSpinner.setAdapter(adapter);
        mFrequencySpinner.setAdapter(adapter);
        mIntervalSpinner.setAdapter(adapter);
    }

    private void initialiseViews(View view){
        mMedicineNameEditText =(AppCompatEditText) view.findViewById(R.id.medicineNameText);
        mTotalQuantityEditText =(AppCompatEditText)view.findViewById(R.id.medicineQuantityText);
        mMonthsToExpireEditText =(AppCompatEditText)view.findViewById(R.id.expireFactoreText);
        mIssueDateEditText =(AppCompatEditText)view.findViewById(R.id.dateIssuedText);
        mPillsBeforeRefillEditText =(AppCompatEditText)view.findViewById(R.id.pillsBeforeRefill);
        mDescriptionEditText =(AppCompatEditText)view.findViewById(R.id.medicineDescriptionText);
        mCategorySpinner =(AppCompatSpinner)view.findViewById(R.id.categorySpinner);
        mDosageSpinner =(AppCompatSpinner)view.findViewById(R.id.dosageSpinner);
        mFrequencySpinner =(AppCompatSpinner)view.findViewById(R.id.frequencySpinner);
        mIntervalSpinner =(AppCompatSpinner)view.findViewById(R.id.intervalSpinner);
        mSaveMedicineButton =(Button)view.findViewById(R.id.saveMedicine);
        mAddDosageTimeButton =(Button)view.findViewById(R.id.dosageTime);
        mAddRefillReminderTime =(Button)view.findViewById(R.id.refillReminderTime);
        mRemindSwitch =(Switch)view.findViewById(R.id.remindSwitch);
    }

    public void setListeners() {
        Button.OnClickListener setTimeListener= new Button.OnClickListener() {

            @Override
            public void onClick(final View v) {
                DialogFragment timerFragment = new TimePickerFragment();
                timerFragment.show(getChildFragmentManager(), Constants.ADD_REMINDER_DIALOG);
            }
        };
        View.OnClickListener saveListener=new View.OnClickListener()
        {
            @Override
            public void onClick(final View v) {
                if(validate()) {
                    try {
                        setMedicineDetails();
                        if(!isEditMedicine) {
                            MediPalApplication.getPersonStore().addMedicine(mMedicine);
                        } else {
                            MediPalApplication.getPersonStore().editMedicine(mMedicine);
                        }

                        StringBuilder builder=new StringBuilder();
                        builder.append(DBConstants.TABLE_MEDICINE);
                        builder.append(Constants.SPACE);
                        builder.append(mMedicine.getMedicine());
                        builder.append(Constants.SPACE);
                        builder.append(Constants.SAVE);

                        Toast.makeText(getContext(),builder.toString(),Toast.LENGTH_SHORT);

                        doBack();
                    } catch (Exception e) {
                        Log.d("error",e.toString());
                        Log.d("Error:", "error in add Medicine Page");
                    }
                }
            }
        };
        View.OnClickListener issueDateListner= new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                showDatePicker();

            }
        };
        View.OnFocusChangeListener focusChangeListener= new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                  showDatePicker();
                }
            }
        };
        mIssueDateEditText.setOnClickListener(issueDateListner);
        mIssueDateEditText.setOnFocusChangeListener(focusChangeListener);

        mAddDosageTimeButton.setOnClickListener(setTimeListener);
        mAddRefillReminderTime.setOnClickListener(setTimeListener);
        mSaveMedicineButton.setOnClickListener(saveListener);
    }


    public static AlertDialog.Builder newMessageDialog(Context context,
                                                       String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.ok_text), null);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public void showDatePicker()
    {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mIssueDateEditText.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        
        datePickerDialog.show();
    }

    public boolean validate()
    {
        if(TextUtils.isEmpty(mMedicineNameEditText.getText())) {
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Medicine name").show();
            return false;
        } else if(TextUtils.isEmpty(mReminderStartTime)){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Start time").show();
            return false;
        } else if(mFrequencySpinner.getSelectedItemPosition() <= 0){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Valid frequency").show();
            return false;
        } else if(mIntervalSpinner.getSelectedItemPosition() <= 0){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Valid interval").show();
            return false;
        } else if(mDosageSpinner.getSelectedItemPosition() <= 0){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Valid dosage").show();
            return false;
        } else if(TextUtils.isEmpty(mIssueDateEditText.getText())){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    "Enter Valid issue date").show();
            return false;
        }

        return true;

    }

    public void setMedicineDetails()
    {
        String medName = String.valueOf(mMedicineNameEditText.getText());
        String medDescription = String.valueOf(mDescriptionEditText.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.ISSUE_DATE_FORMAT);
        int quantity = Integer.parseInt(mTotalQuantityEditText.getText().toString());
        mReminder.setInterval(Integer.valueOf(mSpinnerItems.get(mIntervalSpinner.getSelectedItemPosition())));
        mReminder.setFrequency(Integer.valueOf(mSpinnerItems.get(mFrequencySpinner.getSelectedItemPosition())));
        mMedicine.setMedicine(medName);
        mMedicine.setDescription(medDescription);
        mMedicine.setQuantity(quantity);
        mMedicine.setCatId(((Category)mCategorySpinner.getSelectedItem()).getId());
        mMedicine.setReminder(mReminder);
        mMedicine.setRemind(mRemindSwitch.isChecked());
        mMedicine.setDosage(Integer.parseInt(mDosageSpinner.getSelectedItem().toString()));
        mMedicine.setThreshold(Integer.parseInt(String.valueOf(TextUtils
                .isEmpty(mPillsBeforeRefillEditText.getText())?"0":mPillsBeforeRefillEditText.getText())));
        mMedicine.setExpireFactor(Integer.parseInt(String.valueOf(TextUtils
                .isEmpty(mMonthsToExpireEditText.getText())?"0":mMonthsToExpireEditText.getText())));
        try {
            mMedicine.setDateIssued(dateFormat.parse(mIssueDateEditText.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void updateMedicineDetails()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.ISSUE_DATE_FORMAT);
        mMedicineNameEditText.setText(mMedicine.getMedicine());
        mDosageSpinner.setSelection(mMedicine.getDosage());
        mCategorySpinner.setSelection(mMedicine.getCatId() - 1);
        mTotalQuantityEditText.setText(String.valueOf(mMedicine.getQuantity()));
        mPillsBeforeRefillEditText.setText(String.valueOf(mMedicine.getThreshold()));
        mDescriptionEditText.setText(mMedicine.getDescription());
        mIssueDateEditText.setText(String.valueOf(dateFormat.format(mMedicine.getDateIssued())));
        mRemindSwitch.setChecked(mMedicine.isRemind());
        mMonthsToExpireEditText.setText(String.valueOf(mMedicine.getExpireFactor()));
    }

    public void updateReminderDetails()
    {
        mReminder = mMedicine.getReminder();
        if(mReminder != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mReminder.getStartTime());
            String doseTime = AppHelper.convert24TimeTo12String(cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE));
            mReminderStartTime = AppHelper.convertTimeFormat(doseTime, Constants.TIME_FORMAT_STORAGE,
                    Constants.TIME_12_HOUR_FORMAT);
            mAddDosageTimeButton.setText(mReminderStartTime);
            mFrequencySpinner.setSelection(mReminder.getFrequency());
            mIntervalSpinner.setSelection(mReminder.getInterval());
        }
    }
}
