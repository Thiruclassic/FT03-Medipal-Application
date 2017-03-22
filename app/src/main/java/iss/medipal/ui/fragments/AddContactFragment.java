package iss.medipal.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.InCaseofEmergencyContact;
import iss.medipal.util.DialogUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {


    private AppCompatEditText mNameEdit;
    private AppCompatEditText mNumberEdit;
    private AppCompatEditText mDescriptionEdit;
    private RadioGroup mTypeGroup;
    private RadioButton mTypeSelect;
    private AppCompatEditText mPriorityEdit;
    private Button mSubmitButton;
    private InCaseofEmergencyContact mContact;



    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseViews(view);
        mTypeGroup = (RadioGroup) view.findViewById(R.id.contact_type_group);
        mTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mTypeSelect = (RadioButton) view.findViewById(checkedId);
            }
        });
        setListeners();
    }

    private void initialiseViews(View view)
    {
        mNameEdit = (AppCompatEditText) view.findViewById(R.id.contact_name);
        mNumberEdit = (AppCompatEditText) view.findViewById(R.id.contact_number);
        mDescriptionEdit = (AppCompatEditText) view.findViewById(R.id.contact_description);
        mTypeGroup = (RadioGroup) view.findViewById(R.id.contact_type_group);
        mTypeSelect = (RadioButton) view.findViewById(mTypeGroup.getCheckedRadioButtonId());
        mPriorityEdit = (AppCompatEditText) view.findViewById(R.id.contact_sequence);
        mSubmitButton = (Button) view.findViewById(R.id.submit_details_btn);
    }

    private boolean isValid(){
        if(TextUtils.isEmpty(mNameEdit.getText()) ||
                TextUtils.isEmpty(mNumberEdit.getText()) ||
                TextUtils.isEmpty(mDescriptionEdit.getText()) ||
                TextUtils.isEmpty(mPriorityEdit.getText())){
            DialogUtility.newMessageDialog(getActivity(), getString(R.string.warning),
                    getString(R.string.enter_all_details_text)).show();
            return false;
        }
        return true;
    }

    private void setListeners()
    {
        mSubmitButton.setOnClickListener(mSubmitListener);
    }

    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                if(isValid())
                {
                    mContact = new InCaseofEmergencyContact();
                    mContact.setContactName(mNameEdit.getText().toString());
                    mContact.setContactNo(Long.parseLong(mNumberEdit.getText().toString()));
                    mContact.setDescription(mDescriptionEdit.getText().toString());
                    mContact.setSequence(Integer.parseInt(mPriorityEdit.getText().toString()));
                    switch (mTypeSelect.getText().toString()){
                        case "FAMILY":
                            mContact.setContactType(Constants.FAMILY_CONTACT);
                            break;
                        case "FRIEND":
                            mContact.setContactType(Constants.FRIEND_CONTACT);
                            break;
                        case "DOCTOR":
                            mContact.setContactType(Constants.DOCTOR_CONTACT);
                            break;
                    }
                    MediPalApplication.getPersonStore().addContact(mContact);
                    Context context = getContext();
                    CharSequence text = "Successfully Added Contact";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            } catch (Exception e) {
                Log.d("error",e.toString());
                Log.d("Error:", "error in Add Contact Page");
            }
        }
    };




    /*mTypeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        // checkedId is the RadioButton selected
    }
    });
*/


}
