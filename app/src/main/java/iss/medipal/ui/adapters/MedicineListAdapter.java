package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import iss.medipal.R;
import iss.medipal.model.Medicine;

/**
 * Created by junaidramis on 17/3/17.
 */

public class MedicineListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Medicine> mMedicines;

    public MedicineListAdapter(Context context , ArrayList<Medicine> medicines) {
        this.mContext = context;
        this.mMedicines = medicines;
    }

    @Override
    public int getCount() {
        return mMedicines.size();
    }

    @Override
    public String getItem(int i) {
        return mMedicines.get(i).getMedicine();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MedicineListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_med, parent, false);
            viewHolder = new MedicineListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MedicineListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTextview.setText(mMedicines.get(position).getMedicine());
        return convertView;
    }

    public void setMedicines(ArrayList<Medicine> medicines){
        this.mMedicines = medicines;
        notifyDataSetChanged();
    }

    /**
     * Static view holder class.
     */
    class ViewHolder {
        TextView mItemTextview;

        public ViewHolder(View view) {
            mItemTextview = (TextView) view.findViewById(R.id.med_item_tv);
        }

    }
}
