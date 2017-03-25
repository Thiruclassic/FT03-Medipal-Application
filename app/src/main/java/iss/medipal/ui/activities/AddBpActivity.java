package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.util.DialogUtility;

public class AddBpActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSystolic, etDiastolic, measuredOn;
    private Button saveBloodPressure;
    private TextInputLayout inputLayoutSystolic, inputLayoutDiastolic, inputLayoutMeasuredOn;
    private Toolbar toolbar;
    private MeasurementDao measurementDao;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;


    private ImageView imageBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bp);

/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Blood pressure readings ...(mmHg)");*/

        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        title.setText("Add Blood pressure readings (mmHg)");
        imageBack.setVisibility(View.VISIBLE);

        findViewsById();
        setListeners();

    }

    private void findViewsById() {
        saveBloodPressure = (Button) findViewById(R.id.saveBp);

        inputLayoutSystolic = (TextInputLayout) findViewById(R.id.inputLayoutSystolic);
        inputLayoutDiastolic = (TextInputLayout) findViewById(R.id.inputLayoutDiastolic);
        inputLayoutMeasuredOn = (TextInputLayout) findViewById(R.id.inputLayoutMeasuredOn);
        etSystolic = (EditText) findViewById(R.id.etSystolic);
        etDiastolic = (EditText) findViewById(R.id.etDiastolic);
        measuredOn = (EditText) findViewById(R.id.etMeasuredOn);

    }

    private void setListeners() {

        saveBloodPressure.setOnClickListener(this);

        View.OnClickListener appDateListner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDatePicker();

            }
        };
        measuredOn.setOnClickListener(appDateListner);
        measuredOn.setOnFocusChangeListener(mDateFocusListener);

        ImageView.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click",String.valueOf(v));
                finish();
            }
        };
        imageBack.setOnClickListener(clickListener);

    }

    private View.OnFocusChangeListener mDateFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mDatePickerDialog = showDatePicker();
                mDatePickerDialog.show();
            }
        }
    };

    public DatePickerDialog showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBpActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                measuredOn.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        datePickerDialog.show();
        return datePickerDialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBp:
                if (validateBloodPressureDetails()) {
                    try {

                        String measurement = addMeasurement();
                        Snackbar.make(v, "Saved reading .... !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } catch (Exception ex) {
                        Log.d("error", ex.toString());
                        Log.d("Error:", "error in add Appointment Page");

                    }
                    Intent intent = new Intent(AddBpActivity.this, BloodPressureActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
        }
    }

    public Boolean validateBloodPressureDetails(){


       if (TextUtils.isEmpty(etSystolic.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Systolic in mmHg").show();
            return false;
        } else if (TextUtils.isEmpty(etDiastolic.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Diastolic in  mmhg").show();
            return false;
        } else if (TextUtils.isEmpty(measuredOn.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Measured on time").show();
            return false;
        } else if(!(TextUtils.isEmpty(etSystolic.getText())&&(!TextUtils.isEmpty(etDiastolic.getText())))){
           Integer systolic = Integer.parseInt(String.valueOf(etSystolic.getText()));
           Integer diastolic = Integer.parseInt(String.valueOf(etDiastolic.getText()));
           if(systolic>191 || diastolic >101) {
               DialogUtility.newMessageDialog(this, getString(R.string.warning),
                       "Check the value of systolic and diastolic entered !!").show();
               return false;
           }
        }
        return true;
    }
    public String addMeasurement() throws Exception {
        BloodPressure measurement = getMeasurementDetails();
        measurementDao = MeasurementDaoImpl.newInstance(AddBpActivity.this);
        measurementDao.addBloodPressure(measurement);

        return "Blood Pressure Added !";
    }

    public BloodPressure getMeasurementDetails() throws Exception {

        BloodPressure measurement = null;

        Integer systolic = Integer.parseInt(String.valueOf(etSystolic.getText()));
        Integer diastolic = Integer.parseInt(String.valueOf(etDiastolic.getText()));
        Calendar date = Calendar.getInstance();
        date.setTime(dateFormatter.parse(String.valueOf(measuredOn.getText())));
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateTime.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateTime.set(Calendar.YEAR, date.get(Calendar.YEAR));
        try {
            measurement = new BloodPressure(systolic.intValue(), diastolic.intValue(), dateTime.getTime());
         /*   measurement.setMeasuredOn(dateTime.getTime());
            measurement.setDiastolic(diastolic);
            measurement.setSystolic(systolic);*/

        } catch (Exception ex) {

            Toast.makeText(AddBpActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }
}
