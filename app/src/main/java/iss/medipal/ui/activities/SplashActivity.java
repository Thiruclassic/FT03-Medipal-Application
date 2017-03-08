package iss.medipal.ui.activities;

import android.os.Bundle;
import android.os.Handler;

import iss.medipal.R;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */

public class SplashActivity extends BaseFullScreenActivity {

    private static final int SPLASH_TIME = 2000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPreferenceManager.isAppInitialLaunch(SplashActivity.this)) {
                    launchActivity(UserProfileActivity.class);
                } else {
                    launchActivity(MainActivity.class);
                }
            }
        },SPLASH_TIME);
    }
}
