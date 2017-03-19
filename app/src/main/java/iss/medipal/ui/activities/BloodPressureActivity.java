package iss.medipal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;

import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.Measurement;
import iss.medipal.model.MeasurementType;
import iss.medipal.ui.adapters.RecyclerAdapter;
import iss.medipal.ui.fragments.AddBloodPressureFragment;

/**
 * Created by Sreekumar on 3/17/2017.
 */

public class BloodPressureActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MeasurementDao measurementDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodpressure);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Blood Pressure");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(this,getData());
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
                LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(this);
                mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
                break;

            case R.id.add_blood:
                Intent intent = new Intent(BloodPressureActivity.this, AddBpActivity.class);
                startActivity(intent);
              /*  AddBloodPressureFragment addBloodPressureFragment = AddBloodPressureFragment.newInstance("bp","bp1");
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.add_blood_pressure,addBloodPressureFragment).commit();*/
                break;
        }
        return true;

    }

    public List<MeasurementType> getData() {

        List<MeasurementType> data = new ArrayList<>();
        measurementDao = MeasurementDaoImpl.newInstance(BloodPressureActivity.this);
        List<Measurement> measurementList= measurementDao.getBloodPressureValues();

        int[] images = {
                R.drawable.heart_pulse,
                R.drawable.pulse,
                R.drawable.oil_temperature,
                R.drawable.weight_kilogram
        };

        String[] Categories = {"Blood Pressure","Pulse","Temperature", "Weight"};

        for (int i = 0; i < measurementList.size(); i++) {

            MeasurementType current = new MeasurementType();
            current.setSystolic(String.valueOf(measurementList.get(i).getSystolic()));
            current.setDiastolic(String.valueOf(measurementList.get(i).getDiastolic()));
            current.setMeasuredOn(measurementList.get(i).getMeasuredOn().toString());
            current.setImageType(images[0]);
            data.add(current);
        }

        return data;
    }

}
