package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;
import iss.medipal.model.InCaseofEmergencyContact;

/**
 * Created by Manish on 19/3/2017.
 */

public class FamilyAdapter extends ArrayAdapter<InCaseofEmergencyContact> {

    private Context context;
    private List<InCaseofEmergencyContact> familyMembers = new ArrayList<>();

    public FamilyAdapter(Context context, ArrayList<InCaseofEmergencyContact> familyMembers) {
        super(context, R.layout.row_layout);
        this.context = context;
        this.familyMembers = familyMembers;
    }

    static class ViewHolder{
        TextView tvName;

        public ViewHolder(View view){
            tvName = (TextView)view.findViewById(R.id.tv_name);
        }
    }

    @Override
    public int getCount() {
        return familyMembers.size();
    }

    public void setFamilyContacts(ArrayList<InCaseofEmergencyContact> familyMembers){
        this.familyMembers = familyMembers;
        notifyDataSetChanged();
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
            viewHolder = (FamilyAdapter.ViewHolder) convertView.getTag();
        }

        final InCaseofEmergencyContact contacts = familyMembers.get(position);
        viewHolder.tvName.setText(contacts.getContactName());
        return convertView;
    }
}
