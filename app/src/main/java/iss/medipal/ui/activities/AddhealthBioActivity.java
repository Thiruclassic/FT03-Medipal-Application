package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.constants.DBConstants;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.util.DialogUtility;

/**
 * Created by Mridul on 19-03-2017.
 */

public class AddhealthBioActivity extends AppCompatActivity {


    private static final String ARGS_HEALTH= "ARGS_HEALTH";
    private AppCompatEditText mCondition;
    private AppCompatEditText mStartDate;
    private SimpleDateFormat mDateFormatter;
    private DatePickerDialog mDatePickerDialog;

    private RadioGroup mConditionType_Rg;
    private Button mSubmitButton;
    private RadioButton mConditionType_Rb;
    private RadioButton mConditionType_allergy;
    private RadioButton mConditionType_condition;

    private ImageView imageBack;
    private TextView title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthbio);
        mCondition = (AppCompatEditText) findViewById(R.id.condition_edit);
        mStartDate = (AppCompatEditText) findViewById(R.id.start_date_edit);
        mConditionType_Rg = (RadioGroup) findViewById(R.id.radio_group);
        mSubmitButton = (Button) findViewById(R.id.submit_bttn);
        mConditionType_allergy = (RadioButton) findViewById(R.id.radioButtonAllergy);
        mConditionType_condition = (RadioButton) findViewById(R.id.radioButtonCondition);
        mDateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        mStartDate.setOnClickListener(mDateClickListener);
        mStartDate.setOnFocusChangeListener(mDateFocusListener);
        mSubmitButton.setOnClickListener(mSubmitListener);
        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Enter HealthBio");
        imageBack.setVisibility(View.VISIBLE);
        Intent i = getIntent();
        if(i.getExtras()!=null)
        {
            setData(i.getExtras());
        }

        setListeners();


    }

    private DatePickerDialog getDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mStartDate.setText(mDateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return dialog;
    }

    private View.OnClickListener mDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDatePickerDialog = getDialog();
            mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            mDatePickerDialog.show();
        }
    };
    private View.OnFocusChangeListener mDateFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mDatePickerDialog = getDialog();
                mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePickerDialog.show();
            }
        }
    };


    private boolean isValid() {
        if (TextUtils.isEmpty(mCondition.getText()) ||
                TextUtils.isEmpty(mStartDate.getText())) {
            DialogUtility.newMessageDialog(this, getString(R.string.warning),
                    getString(R.string.enter_all_details_text)).show();
            return false;
        }
        return true;
    }


    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveHealthBio();

        }
    } ;

    private void saveHealthBio(){
        if (isValid()) {
            HealthBio healthBio = new HealthBio();
            healthBio.setCondition(mCondition.getText().toString());
            healthBio.setStartDate(mStartDate.getText().toString());
            int Rb_id=mConditionType_Rg.getCheckedRadioButtonId();
            mConditionType_Rb= (RadioButton)findViewById(Rb_id);
            healthBio.setConditionType(mConditionType_Rb.getTag().toString());
            MediPalApplication.getPersonStore().addHealthBio(healthBio);
            finish();
        }
    }





    public void setData(Bundle bundle)
    {
        HealthBio healthBio=bundle.getParcelable(DBConstants.TABLE_HEALTH_BIO);
        mCondition.setText(healthBio.getCondition());
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.HEALTH_BIO_DATE_TIME_FORMAT);
            date = sdf.parse(healthBio.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStartDate.setText(mDateFormatter.format(date));
        if(healthBio.getConditionType().equalsIgnoreCase("C"))
            mConditionType_condition.setChecked(true);
        else
            mConditionType_allergy.setChecked(true);

        mCondition.setEnabled(false);
        mStartDate.setEnabled(false);
       mConditionType_condition.setEnabled(false);
       mConditionType_allergy.setEnabled(false);
        mSubmitButton.setVisibility(View.INVISIBLE);

    }

    public void setListeners()
    {
        ImageView.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click",String.valueOf(v));
                finish();
            }
        };
        imageBack.setOnClickListener(clickListener);
    }


}
