package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.model.Appointment;

/**
 * Created by sreekumar on 3/21/2017.
 */

public class AppointmentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Appointment> appointments;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm", Locale.getDefault());

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
            viewHolder = new AppointmentListAdapter.ViewHolder(convertView,mContext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppointmentListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTextview1.setText(appointments.get(position).getLocation());
        viewHolder.mItemTextview2.setText(timeFormatter.format(appointments.get(position).getAppointment()));
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
        TextView mItemTextview1;
        TextView mItemTextview2;
        ImageView imgThumb;

        public ViewHolder(View view,Context context) {
            mItemTextview1 = (TextView) view.findViewById(R.id.tvNote);
            mItemTextview2= (TextView) view.findViewById(R.id.tvTime);
            imgThumb =(ImageView) view.findViewById(R.id.img_row1);
            imgThumb.setColorFilter(context.getResources().getColor(R.color.grey_700));

        }
    }
}