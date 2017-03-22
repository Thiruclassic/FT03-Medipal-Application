package iss.medipal.ui.adapters;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.model.Medicine;
import iss.medipal.model.Reminder;

/**
 * Created by Thirumal on 20/3/2017.
 */

public class MedicineReminderListAdapter extends BaseAdapter {

    Context mContext;
    List<Medicine> medicines;

    public MedicineReminderListAdapter(Context mContext,List<Medicine> medicines)
    {
        this.mContext=mContext;
        this.medicines=medicines;
    }


    @Override
    public int getCount() {
        return medicines.size();
    }

    @Override
    public String getItem(int position) {
        return String.valueOf(medicines.get(position).getId());
    }

    @Override
    public long getItemId(int position) {
        return medicines.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MedicineReminderListAdapter.RemindViewHolder viewHolder=null;
        if(convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_reminder, parent, false);
            viewHolder =new MedicineReminderListAdapter.RemindViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(MedicineReminderListAdapter.RemindViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(medicines.get(position).getMedicine());
        viewHolder.switcher.setChecked(medicines.get(position).isRemind());
        viewHolder.switcher.setTag(String.valueOf(position));

        Log.d("Switcher ",String.valueOf(viewHolder.switcher.getTag())+viewHolder.switcher.isChecked());

        setSwitchListeners(viewHolder.switcher);
        return convertView;
    }
    public void setSwitchListeners(Switch switcher)
    {
        Switch.OnCheckedChangeListener listener= new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Medicine medicine=medicines.get(Integer.parseInt(String.valueOf(buttonView.getTag())));
                medicine.setRemind(isChecked);
            }
        };
       Switch.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine medicine=medicines.get(Integer.parseInt(String.valueOf(v.getTag())));
                medicine.setRemind(!medicine.isRemind());
                MediPalApplication.getPersonStore().editMedicine(medicines.get(Integer.parseInt(String.valueOf(v.getTag()))));
            }
        };


        //switcher.setOnCheckedChangeListener(listener);
        switcher.setOnClickListener(clickListener);

    }


    public void setMedicines(List<Medicine> medicines)
    {
        this.medicines=medicines;
        notifyDataSetChanged();
    }

    /*Inner Class View Holder*/
    class RemindViewHolder
    {
        TextView textView;
        Switch switcher;
        RemindViewHolder(View view)
        {
            textView=(TextView)view.findViewById(R.id.medRemindItem);
            switcher=(Switch)view.findViewById(R.id.medRemindItemSwitch);
        }
    }
}


