package iss.medipal.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.dao.AppointmentDao;
import iss.medipal.dao.impl.AppointmentDaoImpl;
import iss.medipal.model.Appointment;
import iss.medipal.util.AppHelper;

/**
 * Created by sreekumar on 3/21/2017.
 */

public class AppointmentListAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private List<Appointment> appointments;
    private AppointmentDao appointmentDao;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private onAppointmentDeletedInterface appointmentDeletedInterface;

    public AppointmentListAdapter(Context context, List<Appointment> appointments, Fragment fragment) {

        this.mContext = context;
        this.appointments = appointments;
        this.appointmentDao = AppointmentDaoImpl.newInstance(context);
        this.appointmentDeletedInterface = (onAppointmentDeletedInterface) fragment;
    }
    @Override
    public int getCount() {
        if (!AppHelper.isListEmpty(appointments)) {
            return appointments.size();
        } else {
            return 0;
        }
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
            viewHolder = new AppointmentListAdapter.ViewHolder(convertView, mContext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppointmentListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTextview1.setText(appointments.get(position).getLocation());
        viewHolder.mItemTextview2.setText(dateFormatter.format(appointments.get(position).getAppointment()) + " " + timeFormatter.format(appointments.get(position).getAppointment()));

        ImageView deleteBtn = (ImageView) convertView.findViewById(R.id.img_row_delete);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialogStyle);
                builder.setCancelable(false);
                builder.setTitle("WARNING");
                builder.setMessage("Do you want to delete permanently ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int arg1) {

                        appointmentDeletedInterface.onAppDeleted(appointments.get(position));
                        Toast.makeText(mContext, "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        alert.dismiss();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int arg1) {
                        alert.dismiss();
                    }
                });
                builder.show();
            }
        });

        return convertView;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    /**
     * Static view holder class.
     */
    class ViewHolder {

        TextView mItemTextview1;
        TextView mItemTextview2;
        ImageView imgThumb;

        public ViewHolder(View view, Context context) {
            mItemTextview1 = (TextView) view.findViewById(R.id.tvNote);
            mItemTextview2 = (TextView) view.findViewById(R.id.tvTime);
            imgThumb = (ImageView) view.findViewById(R.id.img_row1);
            imgThumb.setColorFilter(context.getResources().getColor(R.color.grey_700));

        }
    }
    public interface onAppointmentDeletedInterface {
        void onAppDeleted(Appointment appointment);
    }
}