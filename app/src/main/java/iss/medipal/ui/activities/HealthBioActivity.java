package iss.medipal.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.ui.fragments.HealthBioFragment;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;


/**
 * Created by Mridul on 16-03-2017.
 */

public class HealthBioActivity extends AppCompatActivity implements HealthBioFragment.onBioChangeListener {

    private ImageView imageBack;
    private TextView title;
    private TextView skipText;

    private boolean isInitial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthbio_container);
        if(getIntent().getExtras() != null){
            Bundle bun = getIntent().getExtras().getBundle(Constants.ACTIVITY_EXTRAS);
            isInitial = bun.getBoolean(Constants.ACTIVITY_HEALTH_EXTRAS, false);
        }
        AppHelper.addFragment(this, HealthBioFragment.newInstance());
        imageBack = (ImageView) findViewById(R.id.toolbar_left_icon);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.health_bio_title);
        imageBack.setVisibility(View.VISIBLE);

        skipText = (TextView)findViewById(R.id.toolbar_right_text);
        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HealthBioActivity.this, MainActivity.class);
                   startActivity(intent1);
                finish();
            }
        });
        setListeners();
        setUi();
    }

    private void setUi(){
        if(isInitial){
            imageBack.setVisibility(View.GONE);
            skipText.setVisibility(View.VISIBLE);
            skipText.setFocusable(false);
        } else {
            imageBack.setVisibility(View.VISIBLE);
            skipText.setVisibility(View.GONE);
        }
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

    @Override
    public void onBioChangeListener(int count) {
        if(count == 0){
            skipText.setText(getString(R.string.skip));
        } else {
            skipText.setText(getString(R.string.done));
        }
    }
}
