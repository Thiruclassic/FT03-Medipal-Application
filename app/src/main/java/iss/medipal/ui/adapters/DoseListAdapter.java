package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Medicine;
import iss.medipal.model.homeMedicineModels.MedDoseModel;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 19/3/17.
 */

public class DoseListAdapter extends BaseAdapter {

    public static final String CHANGE_TOOK = "CHANGE_TOOK";

    private ArrayList<MedDoseModel> mDoseRecords;
    private ArrayList<Medicine> mMeds;
    private Medicine mCurrentMed;
    private Context mContext;
    private int mSelectedPosition = -1;


    public DoseListAdapter(Context context , ArrayList<MedDoseModel> doseRecords, ArrayList<Medicine> meds) {
        mDoseRecords = doseRecords;
        mContext = context;
        mMeds = meds;
        mCurrentMed = getCurrentMed();
    }

    @Override
    public int getCount() {
        return mDoseRecords.size();
    }

    @Override
    public MedDoseModel getItem(int i) {
        return mDoseRecords.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.dose_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setViews(viewHolder, mDoseRecords.get(position), position);
        return convertView;
    }

    private Medicine getCurrentMed(){
        for (int i = 0; i < mMeds.size(); i++) {
            if (mDoseRecords.get(0).getId_med() == mMeds.get(i)
                    .getId()){
                return mMeds.get(i);
            }
        }
        return mMeds.get(0);
    }

    private void setViews(ViewHolder viewHolder, MedDoseModel record, int position){
        String[] time = record.getDoseTime().split("/");
        viewHolder.doseTimeText.setText(AppHelper.formatTime(Integer.parseInt(time[0]),
                Integer.parseInt(time[1])));
        viewHolder.doseCheckBox.setTag(position);
        viewHolder.doseItemLayout.setTag(viewHolder.doseItemLayout.getId(), position);
        if(position == mSelectedPosition){
            setViewBackgroundWithoutResettingPadding(viewHolder.doseItemLayout, R.drawable.grey_rounded_background);
        } else {
            viewHolder.doseItemLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        if (record.getStatus() == (Constants.TOOKIT_STATUS)){
            viewHolder.doseCheckBox.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.check));
        } else {
            viewHolder.doseCheckBox.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.empty));
        }
    }

    public static void setViewBackgroundWithoutResettingPadding(final View v, final int backgroundResId) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundResource(backgroundResId);
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * Static view holder class.
     */
    class ViewHolder {
        ImageView doseCheckBox;
        TextView doseTimeText;
        RelativeLayout doseItemLayout;

        public ViewHolder(View view) {
            doseCheckBox = (ImageView) view.findViewById(R.id.dose_checkbox);
            doseTimeText = (TextView) view.findViewById(R.id.dose_time_text);
            doseItemLayout = (RelativeLayout) view.findViewById(R.id.dose_item_layout);
            doseCheckBox.setOnClickListener(onCheckClickListener);
            doseItemLayout.setOnClickListener(onLayoutClickListener);
        }

        ImageView.OnClickListener onCheckClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = ChangeDoseDialogFragment.newInstance(mCurrentMed.getId_mymed(),
//                        (int)view.getTag());
//                newFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), CHANGE_TOOK);
            }
        };

        ImageView.OnClickListener onNoteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = ChangeDoseDialogFragment.newInstance(mCurrentMed.getId_mymed(),
//                        (int)view.getTag());
//                newFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), CHANGE_TOOK);
            }
        };

        RelativeLayout.OnClickListener onLayoutClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag(view.getId());
                mSelectedPosition = pos;
                notifyDataSetChanged();
//                mBus.post(new DoseItemClickedEvent(mCurrentMed.getId_mymed(), pos));
            }
        };
    }
}
