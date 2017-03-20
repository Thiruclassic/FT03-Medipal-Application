package iss.medipal.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import iss.medipal.R;
import iss.medipal.model.Measurement;

public class MeasurementActivity extends BaseActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener  {

    private Toolbar toolbar;
    private Button bloodButton, tempButton, pulseButton, weightButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Measurements");
        toolbar.inflateMenu(R.menu.menu_main_mes);
        toolbar.setOnMenuItemClickListener(this);

        findViewsById();
        setListeners();

    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
         case R.id.homeMes:
                Intent intentHome = new Intent(MeasurementActivity.this, MainActivity.class);
                startActivity(intentHome);

                break;
        }
        return true;

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
            case R.id.button_weight:
                Intent intentWeight = new Intent(MeasurementActivity.this, WeightActivity.class);
                startActivity(intentWeight);
/*
                Snackbar.make(v, "temp", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
                break;
            case R.id.button_temp:

                Intent intentTemp = new Intent(MeasurementActivity.this, TemperatureActivity.class);
                startActivity(intentTemp);
               /* Snackbar.make(v, "Temperature", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                break;
            case R.id.button_pulse:
                Intent intentPulse = new Intent(MeasurementActivity.this, PulseActivity.class);
                startActivity(intentPulse);
          /*      Snackbar.make(v, "Pulse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                break;
        }
    }
}
