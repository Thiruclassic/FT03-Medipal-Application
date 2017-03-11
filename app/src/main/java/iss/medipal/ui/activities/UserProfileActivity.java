package iss.medipal.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;

import iss.medipal.R;
import iss.medipal.ui.fragments.UserProfileEditFragment;
import iss.medipal.util.AppHelper;
import iss.medipal.util.SharedPreferenceManager;

/**
 * Created by junaidramis on 8/3/17.
 */

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        AppHelper.addFragment(this, UserProfileEditFragment.newInstance(SharedPreferenceManager.isAppInitialLaunch(this)));
    }
}
