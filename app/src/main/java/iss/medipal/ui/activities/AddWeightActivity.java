package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Weight;

/**
 * Created by Sreekumar on 19/3/2017
 */
public class AddWeightActivity extends BaseActivity implements View.OnClickListener {

    private EditText etWeight, etWeightMeasuredOn;
    private Button saveWeight;
    private MeasurementDao measurementDao;
    private Toolbar toolbar;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Weight ...(Kg)");

        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        saveWeight = (Button) findViewById(R.id.saveWeight);
        etWeightMeasuredOn = (EditText) findViewById(R.id.etWeightMeasuredOn);
        etWeight = (EditText) findViewById(R.id.etWeight);


    }

    private void setListeners() {

        saveWeight.setOnClickListener(this);

        View.OnClickListener appDateListner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDatePicker();

            }
        };
        etWeightMeasuredOn.setOnClickListener(appDateListner);
        etWeightMeasuredOn.setOnFocusChangeListener(mDateFocusListener);

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddWeightActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etWeightMeasuredOn.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        datePickerDialog.show();
        return datePickerDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveWeight:

                try {

                    String measurement = addMeasurement();
                    Snackbar.make(v, "Saved reading .... !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (Exception ex) {
                    Log.d("error", ex.toString());
                    Log.d("Error:", "error in add Appointment Page");

                }
                Intent intent = new Intent(AddWeightActivity.this, WeightActivity.class);
                startActivity(intent);
                break;
        }
    }


    public String addMeasurement() throws Exception {
        Weight weight = getMeasurementDetails();
        measurementDao = MeasurementDaoImpl.newInstance(AddWeightActivity.this);
        measurementDao.addWeight(weight);

        return "Weight Added !";
    }

    public Weight getMeasurementDetails() throws Exception {

        Weight measurement = null;

        Integer weight = Integer.parseInt(String.valueOf(etWeight.getText()));

        Calendar date = Calendar.getInstance();
        date.setTime(dateFormatter.parse(String.valueOf(etWeightMeasuredOn.getText())));
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateTime.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateTime.set(Calendar.YEAR, date.get(Calendar.YEAR));
        try {
            measurement = new Weight(dateTime.getTime(), weight.intValue());
         /*   measurement.setMeasuredOn(dateTime.getTime());
            measurement.setDiastolic(diastolic);
            measurement.setSystolic(systolic);*/

        } catch (Exception ex) {

            Toast.makeText(AddWeightActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }
}
