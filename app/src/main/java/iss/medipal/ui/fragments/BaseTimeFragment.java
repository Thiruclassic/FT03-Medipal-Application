package iss.medipal.ui.fragments;

import android.os.Bundle;

/**
 * Created by junaidramis on 18/3/17.
 */

public abstract class BaseTimeFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void onTimeSelected(int hourOfDay, int minute);
}
