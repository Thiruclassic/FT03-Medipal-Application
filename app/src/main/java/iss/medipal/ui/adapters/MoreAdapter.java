package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iss.medipal.R;

/**
 * Created by junaidramis on 11/3/17.
 */

public class MoreAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mMoreTitles;

    public MoreAdapter(Context context , ArrayList<String> moreTitles) {
        this.mContext = context;
        this.mMoreTitles = moreTitles;
    }

    @Override
    public int getCount() {
        return mMoreTitles.size();
    }

    @Override
    public String getItem(int i) {
        return mMoreTitles.get(i);
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
                    inflate(R.layout.item_more, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mMoreItemTextview.setText(mMoreTitles.get(position));
        return convertView;
    }

    /**
     * Static view holder class.
     */
    class ViewHolder {
        TextView mMoreItemTextview;

        public ViewHolder(View view) {
            mMoreItemTextview = (TextView) view.findViewById(R.id.more_item_tv);
        }

    }
}
