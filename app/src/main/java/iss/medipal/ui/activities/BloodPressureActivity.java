package iss.medipal.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;

import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.ui.adapters.RecyclerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sreekumar on 3/17/2017.
 */

public class BloodPressureActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MeasurementDao measurementDao;
    private ImageView imageBack;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
/*        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        title.setText("Blood Pressure");*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Blood Pressure");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        setUpRecyclerView();
//        setListeners();

    }
    @Override
    public void onClick(View v) {

    }
    private void setListeners() {
        ImageView.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", String.valueOf(v));
                finish();
            }
        };
        imageBack.setOnClickListener(clickListener);
    }

    private void setUpRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(this, getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.linearViewHorizontal:
         /*       GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(mGridLayoutManager);*/
                showColorDialog();
                break;

            case R.id.add_blood:
                Intent intent = new Intent(BloodPressureActivity.this, AddBpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.home:
                  finish();
                break;
        }
        return true;

    }

    public void showColorDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.help_mes);
        dialog.setTitle("Color Bands");
        dialog.show();

    }

    public List<Measurement> getData() {

        List<Measurement> data = new ArrayList<>();
        measurementDao = MeasurementDaoImpl.newInstance(BloodPressureActivity.this);
        List<BloodPressure> measurementList = measurementDao.getBloodPressureValues();

        int[] images = {
                R.drawable.heart_pulse,
                R.drawable.pulse,
                R.drawable.oil_temperature,
                R.drawable.weight_kilogram
        };

        String[] Categories = {"Blood Pressure", "Pulse", "Temperature", "Weight"};

        if (!measurementList.isEmpty()) {

            for (int i = 0; i < measurementList.size(); i++) {

                Measurement bloodPressure = new BloodPressure(measurementList.get(i).getId(), images[0], measurementList.get(i).getSystolic(),
                        measurementList.get(i).getDiastolic(), measurementList.get(i).getMeasuredOn());
                   data.add(bloodPressure);
            }
        }

        return data;
    }

}
