package iss.medipal.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iss.medipal.R;
import iss.medipal.model.Category;

/**
 * Created by Thirumal on 22/3/2017.
 */

public class CategoryListAdapter extends BaseAdapter {

    List<Category> mCategories;
    Context mContext;
    public CategoryListAdapter(Context context, List<Category> categories)
    {
        this.mCategories=categories;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryListAdapter.ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
            viewHolder=new CategoryListAdapter.ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.mItemTextview.setText(mCategories.get(position).getCategory());
        return convertView;
    }

    public void setCategories(List<Category> categories)
    {
        this.mCategories=categories;
        notifyDataSetChanged();
    }

    /**
     * view holder class.
     */
    class ViewHolder {
        TextView mItemTextview;

        public ViewHolder(View view) {
            mItemTextview = (TextView) view.findViewById(R.id.tvCategory);
        }

    }
}
