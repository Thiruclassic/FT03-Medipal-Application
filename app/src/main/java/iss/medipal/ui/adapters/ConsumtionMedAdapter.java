package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Consumption;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.homeMedicineModels.ConsumptionDayModel;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumtionMedAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Medicine> mMeds;
    private List<Consumption> mConsumtion;
    private HashMap<Medicine, ArrayList<Consumption>> mItems;
    private SimpleDateFormat mDateFormat;

    public ConsumtionMedAdapter(Context context, List<Medicine> meds, List<Consumption> consumptions) {
        this.mContext = context;
        this.mMeds = meds;
        this.mConsumtion = consumptions;
        mDateFormat = new SimpleDateFormat(Constants.DATE_TIME_12_HOUR_FORMAT);
        setItems();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_consumtion_day_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        try {
            boolean isEmpty = false;
            isEmpty = (AppHelper.isListEmpty(mItems.get(mMeds.get(groupPosition)))?true: false);
            holder.mDate.setText((isEmpty)?"Med not yet consumed":
                    mDateFormat.format(mItems.get(mMeds.get(groupPosition)).get(childPosition).getConsumedOn()));
            holder.mDosage.setText(((isEmpty))?"":String.valueOf(mMeds.get(groupPosition).getDosage()) +
                    ((mMeds.get(groupPosition).getDosage() > 1)?"pills":"pill"));
        } catch (Exception e){

        }
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ParentViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_consumtion_day_parent, null);
            holder = new ParentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        holder.mDateField.setText(mMeds.get(groupPosition).getMedicine().toUpperCase());
        if (isExpanded) {
            holder.mIndicator.setRotation(180);
        } else {
            holder.mIndicator.setRotation(0);
        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(AppHelper.isListEmpty(mItems.get(mMeds.get(groupPosition)))){
            return 1;
        } else {
            return mItems.get(mMeds.get(groupPosition)).size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



    private void setItems(){
        mItems = new HashMap<>();
        for (Medicine med: mMeds){
            mItems.put(med, new ArrayList<Consumption>());
        }
        if(!AppHelper.isListEmpty(mConsumtion)){
            for (Consumption consumption: mConsumtion){
                Iterator<Medicine> iterator = mItems.keySet().iterator();
                while (iterator.hasNext()){
                    Medicine med = iterator.next();
                    if(med.getId() == consumption.getMedicineId()){
                        mItems.get(med).add(consumption);
                        break;
                    }
                }
            }
        }
        for(ArrayList<Consumption> consumption: mItems.values()){
            Collections.sort(consumption);
        }
    }


    public class ParentViewHolder {
        protected TextView mDateField;
        protected ImageView mIndicator;

        public ParentViewHolder(View view) {
            mDateField = (TextView) view.findViewById(R.id.date_tv);
            mIndicator = (ImageView) view.findViewById(R.id.indicator);
        }
    }

    public class ChildViewHolder{
        protected TextView mDate;
        protected TextView mDosage;
        protected TextView mTime;
        protected View mDivider;

        public ChildViewHolder(View view) {
            mDate = (TextView) view.findViewById(R.id.medicine_name);
            mDosage = (TextView) view.findViewById(R.id.dosage);
            mTime = (TextView) view.findViewById(R.id.time);
            mDivider = view.findViewById(R.id.divider);
        }
    }
}
