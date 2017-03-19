package iss.medipal.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;
import iss.medipal.ui.fragments.HealthBioFragment;
import iss.medipal.util.DialogUtility;

public class AddhealthBioActivity extends AppCompatActivity {



    private AppCompatEditText mCondition;
    private AppCompatEditText mStartDate;
    private SimpleDateFormat mTimeFormatter;
    private DatePickerDialog mDatePickerDialog;

    private RadioGroup mConditionType_Rg;
    private Button mSubmitButton;
    private RadioButton mConditionType_Rb;

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
        mTimeFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        mStartDate.setOnClickListener(mTimeClickListener);
        mStartDate.setOnFocusChangeListener(mTimeFocusListener);
        mSubmitButton.setOnClickListener(mSubmitListener);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Enter HealthBio");
        imageBack.setVisibility(View.VISIBLE);


    }

    private DatePickerDialog getDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mStartDate.setText(mTimeFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return dialog;
    }

    private View.OnClickListener mTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDatePickerDialog = getDialog();
            mDatePickerDialog.show();
        }
    };
    private View.OnFocusChangeListener mTimeFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mDatePickerDialog = getDialog();
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

    HealthBioDaoImpl healthBioDao=new HealthBioDaoImpl(this);

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

            int rowId = healthBioDao.createHealthBio(healthBio);

            if(rowId == -1 )
                Toast.makeText(this,getString(R.string.unsuccesful),Toast.LENGTH_SHORT).show() ;
            else {
                Toast.makeText(this, getString(R.string.succesful), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,HealthBioActivity.class);
                startActivity(intent);
            }

        }
    }

}
