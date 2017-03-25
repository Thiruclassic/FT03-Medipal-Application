package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.constants.Constants;
import iss.medipal.R;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 20/3/2017.
 */

public class PriorityAdapter extends BaseAdapter {

    private Context context;
    private List<InCaseofEmergencyContact> sortedContacts = new ArrayList<>();

    public PriorityAdapter(Context context, List<InCaseofEmergencyContact> contacts) {
        this.context = context;
        this.sortedContacts = contacts;
    }

    static class ViewHolder{
        TextView tvName;
        ImageView contactIcon;
        ImageView priorityIcon;
        public ViewHolder(View view){
            tvName = (TextView)view.findViewById(R.id.tv_name);
            contactIcon = (ImageView)view.findViewById((R.id.contact_Icon));
            priorityIcon = (ImageView)view.findViewById(R.id.priority_Icon);
        }
    }

    public void setAllContacts(List<InCaseofEmergencyContact> allContacts){
        this.sortedContacts = allContacts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sortedContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return sortedContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sortedContacts.get(position).getId();
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
            viewHolder.contactIcon = (ImageView) convertView.findViewById(R.id.contact_Icon);
            viewHolder.priorityIcon = (ImageView) convertView.findViewById(R.id.priority_Icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PriorityAdapter.ViewHolder) convertView.getTag();
        }

        final InCaseofEmergencyContact contacts = sortedContacts.get(position);
        switch (contacts.getContactType())
        {
            case Constants.FAMILY_CONTACT:
                viewHolder.contactIcon.setImageResource(R.drawable.family_icon);
                break;
            case Constants.DOCTOR_CONTACT:
                viewHolder.contactIcon.setImageResource(R.drawable.doctor2_icon);
                break;
            case Constants.FRIEND_CONTACT:
                viewHolder.contactIcon.setImageResource(R.drawable.friends);
                break;
            default:
                viewHolder.contactIcon.setImageResource(R.drawable.contacts_icon);
                break;
        }
        switch (contacts.getSequence())
        {
            case Constants.HIGH_PRIORITY:
                viewHolder.priorityIcon.setImageResource(R.drawable.high_priority);
                break;
            case Constants.MED_PRIORITY:
                viewHolder.priorityIcon.setImageResource(R.drawable.med_priority);
                break;
            case Constants.LOW_PRIORITY:
                viewHolder.priorityIcon.setImageResource(R.drawable.low_priority);
                break;
            default:
                viewHolder.priorityIcon.setImageResource(R.drawable.high_priority);
                break;
        }
        viewHolder.tvName.setText(contacts.getContactName());
        return convertView;
    }
}
