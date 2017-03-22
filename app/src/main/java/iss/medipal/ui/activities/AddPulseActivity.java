package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import iss.medipal.model.Pulse;
import iss.medipal.ui.fragments.DatePickerFragment;

/**
 * Created by Sreekumar on 3/20/2017
 */

public class AddPulseActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPulse, etPulseMeasuredOn;
    private Button savePulse;
    private MeasurementDao measurementDao;
    private Toolbar toolbar;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pulse);

        toolbar = (Toolbar) findViewById(R.id.toolbarPulseAdd);
        toolbar.setTitle("Add Pulse ...");

        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        savePulse = (Button) findViewById(R.id.savePulse);
        etPulseMeasuredOn = (EditText) findViewById(R.id.etPulseMeasuredOn);
        etPulse = (EditText) findViewById(R.id.etPulse);


    }

    private void setListeners() {

        savePulse.setOnClickListener(this);

        View.OnClickListener appDateListner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDatePicker();

            }
        };
        etPulseMeasuredOn.setOnClickListener(appDateListner);
        etPulseMeasuredOn.setOnFocusChangeListener(mDateFocusListener);

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddPulseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etPulseMeasuredOn.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

//        datePickerDialog.show();
        return datePickerDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.savePulse:

                try {

                    String measurement = addMeasurement();
                    Snackbar.make(v, "Saved reading .... !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (Exception ex) {
                    Log.d("error", ex.toString());
                    Log.d("Error:", "error in add Appointment Page");

                }
                Intent intent = new Intent(AddPulseActivity.this, PulseActivity.class);
                startActivity(intent);
                break;
        }
    }


    public String addMeasurement() throws Exception {
        Pulse pulse = getMeasurementDetails();
        measurementDao = MeasurementDaoImpl.newInstance(AddPulseActivity.this);
        measurementDao.addPulse(pulse);

        return "Weight Added !";
    }

    public Pulse getMeasurementDetails() throws Exception {

        Pulse measurement = null;

        Integer pulse = Integer.parseInt(String.valueOf(etPulse.getText()));

        Calendar date = Calendar.getInstance();
        date.setTime(dateFormatter.parse(String.valueOf(etPulseMeasuredOn.getText())));
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateTime.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateTime.set(Calendar.YEAR, date.get(Calendar.YEAR));
        try {
            measurement = new Pulse(dateTime.getTime(), pulse.intValue());
         /*   measurement.setMeasuredOn(dateTime.getTime());
            measurement.setDiastolic(diastolic);
            measurement.setSystolic(systolic);*/

        } catch (Exception ex) {

            Toast.makeText(AddPulseActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }

}