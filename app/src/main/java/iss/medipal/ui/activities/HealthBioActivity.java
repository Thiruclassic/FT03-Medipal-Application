package iss.medipal.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.ui.fragments.HealthBioFragment;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;


/**
 * Created by Mridul on 16-03-2017.
 */

public class HealthBioActivity extends AppCompatActivity {

    private ImageView imageBack;
    private TextView title;
    private TextView skipText;



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
        title.setText(R.string.health_bio_title);
        imageBack.setVisibility(View.VISIBLE);

        skipText = (TextView)findViewById(R.id.toolbar_right_text);
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HealthBioActivity.this,MainActivity.class);
                   startActivity(intent1);
                finish();
            }
        });
        setSkip();

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        setListeners();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       setSkip();

    }

    private void setSkip()
    {
        if((SharedPreferenceManager.isAppInitialLaunch(getApplicationContext()))) {
            SharedPreferenceManager.setAppLaunchStatus(this, false);
            skipText.setVisibility(View.VISIBLE);
            skipText.setFocusable(false);
            // Log.e("from",SharedPreferenceManager.isAppInitialLaunch(getContext())+"");

        }
        else
            skipText.setVisibility(View.GONE);
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
