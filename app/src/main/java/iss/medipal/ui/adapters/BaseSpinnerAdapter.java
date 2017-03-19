package iss.medipal.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iss.medipal.R;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 9/3/17.
 */
public class BaseSpinnerAdapter extends ArrayAdapter<String> {

    private List<String> mDataArray;

    public BaseSpinnerAdapter(Context context, int resource,
                              List<String> objects) {
        super(context, resource, objects);
        mDataArray = objects;
    }

    /**
     * Static view holder class.
     */
    static class DropDownViewHolder {
        TextView spinnerText;

        public DropDownViewHolder(View view) {
            spinnerText = (TextView)view.findViewById(R.id.spinnerText);
        }
    }

    /**
     * Static view holder class.
     */
    static class ViewHolder {
        TextView spinnerText;

        public ViewHolder(View view) {
            spinnerText = (TextView)view.findViewById(R.id.spinnerText);
        }
    }

    @Override
    public int getCount() {
        return super.getCount(); // This makes the trick: do not show last item
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.spinner_default_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.spinnerText.setText(mDataArray.get(position));
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        DropDownViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.spinner_item, parent, false);
            viewHolder = new DropDownViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DropDownViewHolder) convertView.getTag();
        }
        viewHolder.spinnerText.setTypeface(null, Typeface.NORMAL);
        viewHolder.spinnerText.setTextColor(ContextCompat.getColor(parent.getContext(),
                R.color.mineShaft));
        viewHolder.spinnerText.setText(mDataArray.get(position));
        return convertView;
    }
}

