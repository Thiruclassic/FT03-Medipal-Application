package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.util.AppHelper;

/**
 * Created by sreek on 3/26/2017.
 */

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.MyViewHolder> {


    private List<Appointment> mData;
    private LayoutInflater mInflater;
    private AppointmentDao appointmentDao;
    private Appointment currentObj = null;
    private Context context;

    public ReminderRecyclerAdapter(Context context, List<Appointment> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.appointmentDao = AppointmentDaoImpl.newInstance(context);
        this.context = context;
    }

    @Override
    public ReminderRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_reminder_app, parent, false);
        ReminderRecyclerAdapter.MyViewHolder holder = new ReminderRecyclerAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReminderRecyclerAdapter.MyViewHolder holder, final int position) {

        currentObj = mData.get(position);
        holder.setData(currentObj, context);


    }

    @Override
    public int getItemCount() {
        if(!AppHelper.isListEmpty(mData))
            return mData.size();
        else
            return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        TextView title1, title2;

        public MyViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.txv_row1);
            title2 = (TextView) itemView.findViewById(R.id.txv_row2);
        }

        public void setData(Appointment current, Context context) {
            this.title1.setText(current.getLocation());
            this.title2.setText(dateFormatter.format(current.getAppointment())
                    + " " + timeFormatter.format(current.getAppointment()));

        }
    }
}
