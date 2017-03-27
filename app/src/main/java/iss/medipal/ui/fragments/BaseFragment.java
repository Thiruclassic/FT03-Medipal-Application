package iss.medipal.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import iss.medipal.R;

/**
 * Created by junaidramis on 8/3/17.
 */

public class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;

    /**
     * Based on Gender set app theme
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.loading_text));
        mProgressDialog.setCancelable(false);
    }
}
