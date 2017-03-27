package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Weight;
import iss.medipal.util.DialogUtility;

/**
 * Created by Sreekumar on 19/3/2017
 */
public class AddWeightActivity extends BaseActivity implements View.OnClickListener {

    private EditText etWeight, etWeightMeasuredOn;
    private Button saveWeight;
    private MeasurementDao measurementDao;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatterShow = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatePickerDialog mDatePickerDialog;

    private ImageView imageBack;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        title.setText("Add Weight readings");
        imageBack.setVisibility(View.VISIBLE);

        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        saveWeight = (Button) findViewById(R.id.saveWeight);
        etWeightMeasuredOn = (EditText) findViewById(R.id.etWeightMeasuredOn);
        etWeightMeasuredOn.setInputType(InputType.TYPE_NULL);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddWeightActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            etWeightMeasuredOn.setText(dateFormatter.format(datenew));
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveWeight:
                if (validateWeightDetails()) {
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
                    finish();
                    break;
                }
        }
    }

    public Boolean validateWeightDetails() {

        if (TextUtils.isEmpty(etWeight.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Weight in Kg").show();
            return false;
        } else if (TextUtils.isEmpty(etWeightMeasuredOn.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    "Enter Measured on time").show();
            return false;
        } else if (!TextUtils.isEmpty(etWeight.getText())) {
            Integer weight = Integer.parseInt(String.valueOf(etWeight.getText()));
            if (weight > 200) {
                DialogUtility.newMessageDialog(this, getString(R.string.warning),
                        "Check the value entered ").show();
                return false;
            }

        }
        return true;
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

        } catch (Exception ex) {

            Toast.makeText(AddWeightActivity.this, "Date format exception" + ex, Toast.LENGTH_SHORT).show();
        }

        return measurement;

    }
}
