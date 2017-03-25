package iss.medipal.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.List;

import iss.medipal.MediPalApplication;
import iss.medipal.R;
import iss.medipal.constants.Constants;
import iss.medipal.model.Medicine;
import iss.medipal.model.PersonStore;
import iss.medipal.ui.fragments.AddMedicineFragment;
import iss.medipal.ui.fragments.ViewMedicineFragment;
import iss.medipal.util.DialogUtility;

/**
 * Created by junaidramis on 17/3/17.
 */
public class MedicineListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Medicine> mMedicines;
    private Fragment mFragment;

    public MedicineListAdapter(Context context , List<Medicine> medicines, Fragment fragment) {
        this.mContext = context;
        this.mMedicines = medicines;
        this.mFragment = fragment;
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

        setClickListener(position,viewHolder.deleteImage);
        return convertView;
    }

    public void setMedicines(List<Medicine> medicines){
        this.mMedicines = medicines;
        notifyDataSetChanged();
    }
    public void setClickListener(final int position, View view)
    {
       final DialogInterface.OnClickListener yesClick=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Medicine medicine=mMedicines.remove(position);
                notifyDataSetChanged();
                MediPalApplication.getPersonStore().deleteMedicine(medicine);
                ((ViewMedicineFragment)mFragment).doCallback();
            }
        };
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.newMessageDialogWIthYesNo(mContext, mContext.getString(R.string.warning),
                        "Are you sure you want to delete "+mMedicines.get(position).getMedicine(),yesClick).show();
            }
        };
        view.setOnClickListener(clickListener);

    }



    /**
     * Static view holder class.
     */
    class ViewHolder {
        TextView mItemTextview;
        ImageView deleteImage;

        public ViewHolder(View view) {
            mItemTextview = (TextView) view.findViewById(R.id.med_item_tv);
            deleteImage=(ImageView)view.findViewById(R.id.deleteMed);
        }

    }
}
