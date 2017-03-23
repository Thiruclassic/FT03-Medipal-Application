package iss.medipal.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Consumption;
import iss.medipal.model.DoseContainer;
import iss.medipal.model.Medicine;

/**
 * Created by junaidramis on 31/10/16.
 */

public class ChangeDoseDialogFragment extends DialogFragment {

    private static final String ARGS_MED = "ARGS_MED";
    private static final String ARGS_STATUS = "ARGS_STATUS";
    private static final String ARGS_INDEX = "ARGS_INDEX";

    private Medicine medicine;
    private int status = 0;
    private int index = 0;

    public static ChangeDoseDialogFragment newInstance(Medicine med, int index, int status) {
        ChangeDoseDialogFragment fragment = new ChangeDoseDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MED, med);
        args.putInt(ARGS_INDEX, index);
        args.putInt(ARGS_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        if(getArguments() != null){
            medicine = getArguments().getParcelable(ARGS_MED);
            index = getArguments().getInt(ARGS_INDEX);
            status = getArguments().getInt(ARGS_STATUS);
        }
        final DoseContainer dc = DoseContainer
                .getInstance(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.home_dose_change_title))

                .setPositiveButton(getString(R.string.took_it_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if(status == Constants.TOOKIT_STATUS){
                                    dismiss();
                                } else {
                                    ((OnedayDoseFragment)getParentFragment()).onTookItClicked(index);
                                    dismiss();
                                }
                            }
                        })

                .setNegativeButton(getString(R.string.missed_it_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if(status != Constants.TOOKIT_STATUS){
                                    dismiss();
                                } else {
                                    ((OnedayDoseFragment)getParentFragment()).onMissedit(index);
                                    dismiss();
                                }
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}