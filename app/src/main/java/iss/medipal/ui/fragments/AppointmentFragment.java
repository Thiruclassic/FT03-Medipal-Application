package iss.medipal.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import iss.medipal.ui.fragments.TimePickerFragment;
import iss.medipal.ui.fragments.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import iss.medipal.R;

/**
 * Created by sreek on 3/6/2017.
 */

public class AppointmentFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText etDate, etStrtTime;
    private Button btnSave;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getActivity().startActivity(new Intent(getActivity(), AppointmentActivity.class));

    }
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_appointment, container, false);
        etDate = (EditText) fragmentView.findViewById(R.id.et_select_date);
        etStrtTime = (EditText) fragmentView.findViewById(R.id.et_select_strt_time);
        btnSave = (Button) fragmentView.findViewById(R.id.btn_save);

        etDate.setText(dateFormatter.format(selectedDate.getTime()));


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");*/

                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        etStrtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment  = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timeDialog");
            }
        });

      /*  etDate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                etDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity().getApplicationContext(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });*/

        /*etStrtTime.setText(timeFormatter.format(currentCal.getTime()));
        View.OnClickListener timeClickListener = new View.OnClickListener() {
            @Override public void onClick(final View v) {
                final EditText editText = (EditText) v;
                TimePickerDialog.OnTimeSetListener timeSetListener =
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                                editText.setText(timeFormatter.format(calendar.getTime()));
                            }
                        };
                Calendar timeCalendar = Calendar.getInstance();
                try {
                    timeCalendar.setTime(timeFormatter.parse(editText.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.generic_error, Toast.LENGTH_SHORT)
                            .show();
                }
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(getActivity().getApplicationContext(), timeSetListener,
                        timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        };
        etStrtTime.setOnClickListener(timeClickListener);*/

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isValid()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Proceed Appointment!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragmentView;
    }

    private boolean isValid() {
        boolean isValid = true;
      /*  if (spnMember.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.mem_select_validation_msg, Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (spnFacility.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.fac_select_validation_msg, Toast.LENGTH_SHORT).show();
            isValid = false;
        }*/

        try {
            Calendar selectedStTime = Calendar.getInstance();
            selectedStTime.setTime(timeFormatter.parse(etStrtTime.getText().toString()));
            Calendar convertedStTime = Calendar.getInstance();
            convertedStTime.set(Calendar.HOUR, selectedStTime.get(Calendar.HOUR));
            convertedStTime.set(Calendar.MINUTE, selectedStTime.get(Calendar.MINUTE));
            convertedStTime.set(Calendar.AM_PM, selectedStTime.get(Calendar.AM_PM));

            Calendar selectedEtTime = Calendar.getInstance();
//            selectedEtTime.setTime(timeFormatter.parse(etEndTime.getText().toString()));
            Calendar convertedEtTime = Calendar.getInstance();
            convertedEtTime.set(Calendar.HOUR, selectedEtTime.get(Calendar.HOUR));
            convertedEtTime.set(Calendar.MINUTE, selectedEtTime.get(Calendar.MINUTE));
            convertedEtTime.set(Calendar.AM_PM, selectedEtTime.get(Calendar.AM_PM));

            if (currentCal.getTime().compareTo(selectedDate.getTime()) > 0) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.date_validation_msg, Toast.LENGTH_SHORT).show();
                isValid = false;
            } else if (currentCal.compareTo(convertedStTime) > 0) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.time_validation_msg, Toast.LENGTH_SHORT).show();
                isValid = false;
            } else if (convertedStTime.compareTo(convertedEtTime) >= 0) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.st_time_et_time_validation_msg, Toast.LENGTH_SHORT).show();
                isValid = false;
            }
        } catch (ParseException e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.generic_error, Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

}
