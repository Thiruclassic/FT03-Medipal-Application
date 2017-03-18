package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import iss.medipal.R;
import iss.medipal.model.MeasurementType;


import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private List<MeasurementType> mData;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context context, List<MeasurementType> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MeasurementType currentObj = mData.get(position);
        holder.setData(currentObj);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.txv_row);
            imgThumb    = (ImageView) itemView.findViewById(R.id.img_row);
        }

        public void setData(MeasurementType current) {
            this.title.setText(current.getType());
            this.imgThumb.setImageResource(current.getImageType());
        }
    }
}
