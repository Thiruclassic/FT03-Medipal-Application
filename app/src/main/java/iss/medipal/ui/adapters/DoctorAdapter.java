package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 18/3/2017.
 */



public class DoctorAdapter extends ArrayAdapter<InCaseofEmergencyContact> {
    private Context context;
    private List<InCaseofEmergencyContact> doctors = new ArrayList<>();

    public DoctorAdapter(Context context, ArrayList<InCaseofEmergencyContact> doctors) {
        super(context, R.layout.row_layout);
        this.context = context;
        this.doctors = doctors;
    }

    static class ViewHolder{
        TextView tvName;

        public ViewHolder(View view){
            tvName = (TextView)view.findViewById(R.id.tv_name);
        }
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
