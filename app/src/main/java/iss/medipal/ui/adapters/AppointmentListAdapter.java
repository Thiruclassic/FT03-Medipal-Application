package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.model.Appointment;

/**
 * Created by sreekumar on 3/21/2017.
 */

public class AppointmentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Appointment> appointments;

    public AppointmentListAdapter(Context context, List<Appointment> appointments) {

        this.mContext = context;
        this.appointments = appointments;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public String getItem(int i) {
        return appointments.get(i).getLocation();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppointmentListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.todo_list_item, parent, false);
            viewHolder = new AppointmentListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppointmentListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTextview.setText(appointments.get(position).getLocation());

        return convertView;
    }

    public void setAppointments(List<Appointment>appointments){
        this.appointments=appointments;
        notifyDataSetChanged();
    }
    /**
     * Static view holder class.
     */
    class ViewHolder {

        //todo add more items to the list view
        TextView mItemTextview;

        public ViewHolder(View view) {
            mItemTextview = (TextView) view.findViewById(R.id.tvNote);
        }
    }
}