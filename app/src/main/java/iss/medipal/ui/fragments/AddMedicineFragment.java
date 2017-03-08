package iss.medipal.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.CategoryDao;
import iss.medipal.dao.MedicineDao;
import iss.medipal.dao.ReminderDao;
import iss.medipal.dao.impl.CategoryDaoImpl;
import iss.medipal.dao.impl.MedicineDaoImpl;
import iss.medipal.dao.impl.ReminderDaoImpl;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;
import iss.medipal.ui.activities.MainActivity;
import iss.medipal.ui.interfaces.CustomBackPressedListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class AddMedicineFragment extends Fragment implements CustomBackPressedListener{



    EditText medicineName;
    EditText description;
    EditText totalQuantity;
    EditText issueDate;
    EditText monthsToExpire;

    EditText pillsBeforeRefill;

    Button saveMedicineButton;
    Button addDosageTimeButton;
    Button addRefillReminderTime;

    Spinner categorySpinner;
    Spinner dosageSpinner;

    Switch remind;
    Switch refillReminder;


    MedicineDao medicineDao;
    ReminderDao reminderDao;
    CategoryDao categoryDao;

    private Medicine medicine;
    private Reminder reminder;


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
    public static AddMedicineFragment newInstance(int medicineId) {
        AddMedicineFragment fragment = new AddMedicineFragment();
        Log.d("ADD Medicine Fragment",String.valueOf(medicineId));
        if(medicineId!=0)
        {
            fragment.medicine=new Medicine();
            fragment.medicine.setId(medicineId);
        }
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
        medicineName=(EditText) thisView.findViewById(R.id.medicineNameText);
        totalQuantity=(EditText)thisView.findViewById(R.id.medicineQuantityText);
        monthsToExpire=(EditText)thisView.findViewById(R.id.expireFactoreText);
        issueDate=(EditText)thisView.findViewById(R.id.dateIssuedText);
        pillsBeforeRefill=(EditText)thisView.findViewById(R.id.pillsBeforeRefill);
        description=(EditText)thisView.findViewById(R.id.medicineDescriptionText);


        categorySpinner=(Spinner)thisView.findViewById(R.id.categorySpinner);
        dosageSpinner=(Spinner)thisView.findViewById(R.id.dosageSpinner);
        saveMedicineButton=(Button)thisView.findViewById(R.id.saveMedicine);
        addDosageTimeButton=(Button)thisView.findViewById(R.id.dosageTime);
        addRefillReminderTime=(Button)thisView.findViewById(R.id.refillReminderTime);

        remind=(Switch) thisView.findViewById(R.id.remindSwitch);

        return thisView;
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
        FragmentManager manager=getActivity().getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.detach(this).commit();
        ((MainActivity)getActivity()).setmListener(null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        categoryDao= CategoryDaoImpl.newInstance();
        List<String> categories = categoryDao.getAllCategories();
        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,categories);

        categorySpinner.setAdapter(categoryAdapter);

        if(medicine!=null)
        {
            updateMedicineDetails();
            updateReminderDetails();
            saveMedicineButton.setText("Modify Medicine");
        }


        setListeners();

    }

    public void setListeners()
    {
        Button.OnClickListener setTimeListener= new Button.OnClickListener() {

            @Override
            public void onClick(final View v) {

              ReminderDialogFragment dialogFragment=ReminderDialogFragment.newInstance(reminder);
                dialogFragment.show(getChildFragmentManager(),Constants.ADD_REMINDER_DIALOG);
            }
        };

        View.OnClickListener saveListener=new View.OnClickListener()
        {
            @Override
            public void onClick(final View v) {


                if(validate()) {

                    try {

                        int reminderId = addReminder();
                        String medicine = addMedicine(reminderId);

                        Toast.makeText(getContext(), medicine + " Medicine successfully saved", Toast.LENGTH_SHORT).show();
                        Log.d("Fragment type", String.valueOf(getParentFragment()));
                        ViewMedicineFragment fragment=(ViewMedicineFragment) getParentFragment();
                        //fragment.medicineNames.add(medicine);
                        fragment.medicineListAdapter.add(medicine);

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

        issueDate.setOnClickListener(issueDateListner);
        issueDate.setOnFocusChangeListener(focusChangeListener);

        addDosageTimeButton.setOnClickListener(setTimeListener);
        addRefillReminderTime.setOnClickListener(setTimeListener);
        saveMedicineButton.setOnClickListener(saveListener);
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
                issueDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        
        datePickerDialog.show();
    }


    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String addMedicine(int reminderId) throws Exception
    {
        Medicine medicine=getMedicineDetails(reminderId);
        medicineDao = MedicineDaoImpl.newInstance();
        if(medicineName.getText().equals("Save Medicine")) {
            medicineDao.addMedicine(medicine);
        }
        else
        {
            medicineDao.updateMedicine(medicine);
        }
        return medicine.getMedicine();
    }

    public Integer addReminder()
    {
        Integer reminderId=null;
        if(reminder!=null)
        {
            reminderDao= ReminderDaoImpl.newInstance();
          reminderId=reminderDao.addReminder(reminder);
        }
        return reminderId;
    }

    public boolean validate()
    {
        boolean checker=true;

        Log.d("enterbefore",String.valueOf(medicineName.getText()));
        String name=String.valueOf(medicineName.getText());

        if(name==null || name.equals(""))
        {
            Log.d("enter","enter");
           newMessageDialog(getContext(),"Name Empty","Enter Medicine Name").show();
            checker=false;

        }

        return checker;

    }

    public Medicine getMedicineDetails(int reminderId) throws Exception
    {
        String medName = String.valueOf(medicineName.getText());
        String medDescription = String.valueOf(description.getText());
        int quantity = Integer.parseInt(totalQuantity.getText().toString());
        String catId = categorySpinner.getSelectedItem().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.parse(String.valueOf(issueDate.getText()));

        Calendar calendar = Calendar.getInstance();

        if(medicine==null)
        {
            medicine=new Medicine();
        }
        medicine.setMedicine(medName);
        medicine.setDescription(medDescription);
        medicine.setQuantity(quantity);
        medicine.setCatId((int)categorySpinner.getSelectedItemId()+1);
        medicine.setReminderId(reminderId);
        medicine.setRemind(remind.isChecked());
        medicine.setDosage(Integer.parseInt(dosageSpinner.getSelectedItem().toString()));
        medicine.setThreshold(Integer.parseInt(String.valueOf(pillsBeforeRefill.getText())));
        medicine.setDateIssued(new Date());
        medicine.setExpireFactor(Integer.parseInt(String.valueOf(monthsToExpire.getText())));
        return medicine;
    }

    public void updateMedicineDetails()
    {

         medicineDao=MedicineDaoImpl.newInstance();

        medicine=medicineDao.getMedicinebyId(medicine.getId());


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Log.d("cbox update testing",String.valueOf(medicine.getDateIssued()));
        medicineName.setText(medicine.getMedicine());
        dosageSpinner.setSelection(medicine.getDosage());
        categorySpinner.setSelection(medicine.getCatId()-1);
        totalQuantity.setText(String.valueOf(medicine.getQuantity()));
        pillsBeforeRefill.setText(String.valueOf(medicine.getThreshold()));
        description.setText(medicine.getDescription());
        issueDate.setText(String.valueOf(dateFormat.format(medicine.getDateIssued())));
        remind.setChecked(medicine.isRemind());

        monthsToExpire.setText(String.valueOf(medicine.getExpireFactor()));

    }

    public void updateReminderDetails()
    {
        reminderDao=ReminderDaoImpl.newInstance();

        reminder= reminderDao.getReminderById(medicine.getReminderId());
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:MM");
        Log.d("reminder1",String.valueOf(medicine.getReminderId()));
        //addDosageTimeButton.setText(timeFormat.format(reminder.getStartTime()));

    }
}
