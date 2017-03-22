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

public class FriendAdapter extends ArrayAdapter<InCaseofEmergencyContact> {
    private Context context;
    private List<InCaseofEmergencyContact> friends = new ArrayList<>();

    public FriendAdapter(Context context, ArrayList<InCaseofEmergencyContact> friends) {
        super(context, R.layout.row_layout);
        this.context = context;
        this.friends = friends;
    }

    static class ViewHolder{
        TextView tvName;

        public ViewHolder(View view){
            tvName = (TextView)view.findViewById(R.id.tv_name);
        }
    }

    public void setFriendContacts(ArrayList<InCaseofEmergencyContact> friends){
        this.friends = friends;
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
            viewHolder = (FriendAdapter.ViewHolder) convertView.getTag();
        }

        final InCaseofEmergencyContact contacts = friends.get(position);
        viewHolder.tvName.setText(contacts.getContactName());
        return convertView;
    }
}
