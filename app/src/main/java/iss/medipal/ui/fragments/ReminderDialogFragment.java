package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import iss.medipal.R;
import iss.medipal.model.Reminder;


/**
 * Created by Thirumal on 3/3/17.
 */

public class ReminderDialogFragment extends DialogFragment {

    Spinner frequencyText;
    Spinner intervalText;
    Button saveReminder;
    TimePicker timePicker;

    Reminder reminder;


    public static ReminderDialogFragment newInstance(Reminder reminder) {

        ReminderDialogFragment fragment = new ReminderDialogFragment();
        fragment.reminder=reminder;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_reminder, container,true);
        frequencyText=(Spinner) view.findViewById(R.id.frequencyText);
        intervalText=(Spinner) view.findViewById(R.id.intervalText);
        timePicker=(TimePicker)view.findViewById(R.id.reminderTime);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveReminder=(Button)view.findViewById(R.id.saveReminder);

        setReminderData();
        setListeners(this);

    }

    public void setListeners(final Fragment currentfragment)
    {
        final View.OnClickListener timeListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager=getChildFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();

                Fragment fragment=getParentFragment();
                if(fragment instanceof AddMedicineFragment)
                {
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                    AddMedicineFragment addMedicineFragment=(AddMedicineFragment)fragment;

                    Reminder reminder=new Reminder();
                    reminder.setFrequency(Integer.parseInt(frequencyText.getSelectedItem().toString()));
                    reminder.setInterval(Integer.parseInt(intervalText.getSelectedItem().toString()));
                    reminder.setStartTime(calendar.getTime());
//                    addMedicineFragment.setmReminder(reminder);
                    transaction.hide(currentfragment).commit();
//                    addMedicineFragment.addDosageTimeButton.setText(addMedicineFragment.setReminderText(reminder));
                }

            }
        };

        saveReminder.setOnClickListener(timeListener);

    }


    public void setReminderData()
    {
        if(reminder!=null) {
            frequencyText.setSelection(reminder.getFrequency());
            intervalText.setSelection(reminder.getInterval());
            timePicker.setIs24HourView(Boolean.TRUE);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(reminder.getStartTime());
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

    }
}
