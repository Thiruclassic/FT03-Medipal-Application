package iss.medipal.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.model.homeMedicineModels.ConsumptionDayModel;
import iss.medipal.model.homeMedicineModels.MedDayModel;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 26/3/17.
 */

public class ConsumtionDateAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ConsumptionDayModel> mItems;
    private Map<String, Medicine> mMeds;
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mTimeFormat;

    public ConsumtionDateAdapter(Context context, List<ConsumptionDayModel> items) {
        this.mContext = context;
        this.mItems = items;
        float density = context.getResources().getDisplayMetrics().density;
        mDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        mTimeFormat = new SimpleDateFormat(Constants.TIME_12_HOUR_FORMAT);
        setMeds();
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
            holder.mMedName.setText(mMeds.get(String.valueOf(groupPosition + "*" + childPosition)).getMedicine());
            holder.mDosage.setText(String.valueOf(mMeds.get(String.valueOf(groupPosition + "*" + childPosition)).getDosage() +
                    ((mMeds.get(String.valueOf(groupPosition + "*" + childPosition)).getDosage() > 1)?"pills":"pill")));
            holder.mTime.setText(mTimeFormat.format(mItems.get(groupPosition)
                    .getConsumptions().get(childPosition).getConsumedOn().getTime()));
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
        holder.mDateField.setText(mDateFormat.format(mItems.get(groupPosition).getDate().getTime()));
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
        return mItems.get(groupPosition).getConsumptions().size();
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



    private void setMeds(){
        mMeds = new HashMap<>();
        PersonStore personStore = MediPalApplication.getPersonStore();
        for(int i = 0; i < mItems.size(); i++){
            for(int j = 0; j < mItems.get(i).getConsumptions().size(); j++){
                mMeds.put(String.valueOf(i + "*" + j), personStore.getMedicinById(
                        mItems.get(i).getConsumptions().get(j).getMedicineId()));
            }
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
        protected TextView mMedName;
        protected TextView mDosage;
        protected TextView mTime;
        protected View mDivider;

        public ChildViewHolder(View view) {
            mMedName = (TextView) view.findViewById(R.id.medicine_name);
            mDosage = (TextView) view.findViewById(R.id.dosage);
            mTime = (TextView) view.findViewById(R.id.time);
            mDivider = view.findViewById(R.id.divider);
        }
    }
}
