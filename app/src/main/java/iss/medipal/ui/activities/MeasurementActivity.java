package iss.medipal.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import iss.medipal.R;

public class MeasurementActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button bloodButton, tempButton, pulseButton, weightButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Measurements");

        findViewsById();
        setListeners();

    }

    private void findViewsById() {
        bloodButton = (Button) findViewById(R.id.button_blood);
        tempButton = (Button) findViewById(R.id.button_temp);
        pulseButton = (Button) findViewById(R.id.button_pulse);
        weightButton = (Button) findViewById(R.id.button_weight);

    }

    private void setListeners() {

        bloodButton.setOnClickListener(this);
        tempButton.setOnClickListener(this);
        pulseButton.setOnClickListener(this);
        weightButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_blood:
               /* Snackbar.make(v, "blood", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MeasurementActivity.this, BloodPressureActivity.class);
                startActivity(intent);
                break;
            case R.id.button_temp:
                Snackbar.make(v, "temp", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.button_weight:
                Snackbar.make(v, "weight", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.button_pulse:
                Snackbar.make(v, "pulse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
