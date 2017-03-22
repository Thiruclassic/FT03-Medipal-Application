package iss.medipal.ui.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.ui.fragments.HealthBioFragment;
import iss.medipal.util.AppHelper;

public class HealthBioActivity extends AppCompatActivity {

    private ImageView imageBack;
    private TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthbio_container);

        /*FragmentManager manager=getSupportFragmentManager();

        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(HealthBioFragment.newInstance(),"HEALTHBIOFRAMENt").commit();*/

        AppHelper.addFragment(this, HealthBioFragment.newInstance());

        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("Health Bio");
        imageBack.setVisibility(View.VISIBLE);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        setListeners();

    }

    public void setListeners()
    {
        ImageView.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        imageBack.setOnClickListener(clickListener);
    }



}
