package iss.medipal.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import iss.medipal.R;
import iss.medipal.dao.HealthBioDao;
import iss.medipal.dao.impl.HealthBioDaoImpl;
import iss.medipal.model.HealthBio;

/**
 * Created by Mridul on 19-03-2017.
 */

public class HealthBioListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HealthBio> mHealthBio;
    HealthBioDao healthBioDao;

    public HealthBioListAdapter(Context mContext, List<HealthBio> mHealthBio) {
        this.mContext = mContext;
        this.mHealthBio = mHealthBio;
    }


    @Override
    public int getCount() {
        return mHealthBio.size();
    }

    @Override
    public Object getItem(int position) {
        return mHealthBio.get(position).getCondition();
    }

    @Override
    public long getItemId(int position) {
        return mHealthBio.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("position",String.valueOf(position));
        healthBioDao = HealthBioDaoImpl.newInstance(mContext);
        HealthBioListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.healthbio_list_item, parent, false);
            viewHolder = new HealthBioListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HealthBioListAdapter.ViewHolder) convertView.getTag();
        }
        Log.d("position",String.valueOf(mHealthBio.get(position).getCondition()));
        viewHolder.mItemTextview.setText(mHealthBio.get(position).getCondition());

        viewHolder.deleteHealthBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthBioDao.deleteHealthBio(mHealthBio.get(position).getId());
                removeHealthBio(position);

            }
        });
        return convertView;
    }

    public void setHealthBio(List<HealthBio> healthbio){
        this.mHealthBio = healthbio;
        notifyDataSetChanged();
    }


    /**
     * Static view holder class.
     */
    class ViewHolder {
        TextView mItemTextview;
        ImageView deleteHealthBio;

        public ViewHolder(View view) {
            mItemTextview = (TextView) view.findViewById(R.id.tvNote);
            deleteHealthBio = (ImageView) view.findViewById(R.id.deletebutton);
        }
    }
    public void removeHealthBio(int position)
    {
        mHealthBio.remove(position);
        setHealthBio(mHealthBio);
    }
}
