package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import java.util.Date;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.Pulse;
import iss.medipal.ui.fragments.DatePickerFragment;
import iss.medipal.util.DialogUtility;

/**
 * Created by Sreekumar on 3/20/2017
 */

public class AddPulseActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPulse, etPulseMeasuredOn;
    private Button savePulse;
    private MeasurementDao measurementDao;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatterShow = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;

    private ImageView imageBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pulse);

        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        title.setText("Add Pulse readings");
        imageBack.setVisibility(View.VISIBLE);

        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        savePulse = (Button) findViewById(R.id.savePulse);
        etPulseMeasuredOn = (EditText) findViewById(R.id.etPulseMeasuredOn);
//        etPulseMeasuredOn.setInputType(InputType.TYPE_NULL);
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


        ImageView.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", String.valueOf(v));
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddPulseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showDate(year, month + 1, dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    private void showDate(int year, int month, int day) {
        try {

            Date datenew = dateFormatterShow.parse(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).toString());
            etPulseMeasuredOn.setText(dateFormatter.format(datenew));
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.savePulse:
                if (validatePulseDetails()) {
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
                    finish();
                    break;
                }
        }
    }

    public Boolean validatePulseDetails() {


        if (TextUtils.isEmpty(etPulse.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Pulse in bpm").show();
            return false;
        } else if (TextUtils.isEmpty(etPulseMeasuredOn.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Measured on time").show();
            return false;
        } else if (!TextUtils.isEmpty(etPulse.getText())) {
            Integer pulse = Integer.parseInt(String.valueOf(etPulse.getText()));
            if (pulse > 200) {
                DialogUtility.newMessageDialog(this, getString(R.string.warning),
                        "Check the pulse value entered !!").show();
                return false;
            }

        }
        return true;
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

        } catch (Exception ex) {

            Toast.makeText(AddPulseActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }

}
