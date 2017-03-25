package iss.medipal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.Measurement;
import iss.medipal.model.Weight;
import iss.medipal.ui.adapters.RecyclerAdapter;

/**
 * Created by Sreekumar on 19/3/2017
 */

public class WeightActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MeasurementDao measurementDao;

    private ImageView imageBack;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        toolbar = (Toolbar) findViewById(R.id.toolbarWeight);
        toolbar.setTitle("Weight");

  /*      imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);

        toolbar.setTitle("Weight");*/
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
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(mGridLayoutManager);
                break;

            case R.id.add_blood:
                Intent intent = new Intent(WeightActivity.this, AddWeightActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.home:
                finish();
                break;
        }
        return true;

    }

    public List<Measurement> getData() {

        List<Measurement> data = new ArrayList<>();
        measurementDao = MeasurementDaoImpl.newInstance(WeightActivity.this);
        List<Weight> weightList = measurementDao.getWeightValues();

        int[] images = {
                R.drawable.heart_pulse,
                R.drawable.pulse,
                R.drawable.oil_temperature,
                R.drawable.weight_kilogram
        };

        String[] Categories = {"Blood Pressure", "Pulse", "Temperature", "Weight"};
        if (!weightList.isEmpty()) {

            for (int i = 0; i < weightList.size(); i++) {

                Measurement weight = new Weight(weightList.get(i).getId(), images[3], weightList.get(i).getWeight(),
                        weightList.get(i).getMeasuredOn());
                data.add(weight);
            }
        }

        return data;
    }
}
