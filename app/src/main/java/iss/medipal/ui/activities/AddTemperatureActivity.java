package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Date;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.Pulse;
import iss.medipal.model.Temperature;
import iss.medipal.util.DialogUtility;

/**
 * Created by Sreekumar on 3/20/2017
 */

public class AddTemperatureActivity extends BaseActivity implements View.OnClickListener {


    private EditText etTemperature, etTempMeasuredOn;
    private Button saveTemperature;
    private MeasurementDao measurementDao;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatterShow = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;
    private ImageView imageBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_temperature);

        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        title.setText("Add Temperature Readings");
        imageBack.setVisibility(View.VISIBLE);

        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        saveTemperature = (Button) findViewById(R.id.saveTemperature);
        etTempMeasuredOn = (EditText) findViewById(R.id.etTemperatureMeasuredOn);
        etTemperature = (EditText) findViewById(R.id.etTemperature);


    }

    private void setListeners() {

        saveTemperature.setOnClickListener(this);

        View.OnClickListener appDateListner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDatePicker();

            }
        };
        etTempMeasuredOn.setOnClickListener(appDateListner);
        etTempMeasuredOn.setOnFocusChangeListener(mDateFocusListener);

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTemperatureActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showDate(year, month + 1, dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        return datePickerDialog;
    }
    private void showDate(int year, int month, int day) {
        try {
            Date datenew = dateFormatterShow.parse(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).toString());
            etTempMeasuredOn.setText(dateFormatter.format(datenew));
        } catch (Exception e) {

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveTemperature:
                if (validateTemperatureDetails()) {
                    try {

                        String measurement = addMeasurement();
                        Snackbar.make(v, "Saved reading .... !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } catch (Exception ex) {
                        Log.d("error", ex.toString());
                        Log.d("Error:", "error in add Appointment Page");

                    }
                    Intent intent = new Intent(AddTemperatureActivity.this, TemperatureActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
        }
    }

    public Boolean validateTemperatureDetails() {

        if (TextUtils.isEmpty(etTemperature.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Temperature in 'C").show();
            return false;
        } else if (TextUtils.isEmpty(etTempMeasuredOn.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Measured on time").show();
            return false;
        } else if (!TextUtils.isEmpty(etTemperature.getText())) {
            Integer temp = Integer.parseInt(String.valueOf(etTemperature.getText()));
            if (temp > 200) {
                DialogUtility.newMessageDialog(this, getString(R.string.warning),
                        "Check the temperature value entered !!").show();
                return false;
            }

        }
        return true;
    }

    public String addMeasurement() throws Exception {
        Temperature temperature = getMeasurementDetails();
        measurementDao = MeasurementDaoImpl.newInstance(AddTemperatureActivity.this);
        measurementDao.addTemperature(temperature);

        return "Weight Added !";
    }

    public Temperature getMeasurementDetails() throws Exception {

        Temperature measurement = null;

        Integer temp = Integer.parseInt(String.valueOf(etTemperature.getText()));

        Calendar date = Calendar.getInstance();
        date.setTime(dateFormatter.parse(String.valueOf(etTempMeasuredOn.getText())));
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateTime.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateTime.set(Calendar.YEAR, date.get(Calendar.YEAR));
        try {
            measurement = new Temperature(temp.intValue(), dateTime.getTime());

        } catch (Exception ex) {

            Toast.makeText(AddTemperatureActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }

}
