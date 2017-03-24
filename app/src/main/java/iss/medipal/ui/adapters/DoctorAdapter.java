package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 18/3/2017.
 */



public class DoctorAdapter extends BaseAdapter {
    private Context context;
    private List<InCaseofEmergencyContact> doctors = new ArrayList<>();

    public DoctorAdapter(Context context, List<InCaseofEmergencyContact> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    static class ViewHolder{
        TextView tvName;

        public ViewHolder(View view){
            tvName = (TextView)view.findViewById(R.id.tv_name);
        }
    }

    public void setDoctorContacts(List<InCaseofEmergencyContact> doctors){
        this.doctors = doctors;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return doctors.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final InCaseofEmergencyContact contacts = doctors.get(position);
        viewHolder.tvName.setText(contacts.getContactName());
        return convertView;
    }
}
