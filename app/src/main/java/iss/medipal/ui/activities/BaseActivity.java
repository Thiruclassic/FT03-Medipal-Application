package iss.medipal.ui.activities;

import android.support.v7.app.AppCompatActivity;

import iss.medipal.ui.interfaces.CustomBackPressedListener;

/**
 * Created by junaidramis on 25/2/17.
 */

public class BaseActivity extends AppCompatActivity {

    private CustomBackPressedListener mListener;

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
}
