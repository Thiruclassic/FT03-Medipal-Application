package iss.medipal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import iss.medipal.constants.Constants;
import iss.medipal.ui.interfaces.CustomBackPressedListener;

/**
 * Created by junaidramis on 25/2/17.
 */

public class BaseActivity extends AppCompatActivity {

    private CustomBackPressedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setmListener(CustomBackPressedListener mListener) {
        this.mListener = mListener;
    }

    public CustomBackPressedListener getmListener()
    {
        return mListener;
    }

    @Override
    public void onBackPressed() {
        if(mListener != null){
            mListener.doBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Launch activity class
     *
     * @param clazz
     */
    protected void launchActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }

    /**
     * Launch activity class
     *
     * @param clazz
     */
    protected void launchActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(Constants.ACTIVITY_EXTRAS, extras);
        startActivity(intent);
        finish();
    }
}
