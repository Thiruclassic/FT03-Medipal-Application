package iss.medipal.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import iss.medipal.R;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;

/**
 * Created by Sreekumar on 3/17/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private List<Measurement> mData;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context context, List<Measurement> data) {
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
        Measurement currentObj = mData.get(position);
        holder.setData(currentObj);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title1,title2;
        ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);
            title1       = (TextView)  itemView.findViewById(R.id.txv_row1);
            title2       = (TextView)  itemView.findViewById(R.id.txv_row2);
            imgThumb    = (ImageView) itemView.findViewById(R.id.img_row1);

        }

        public void setData(Measurement current) {

            if(current instanceof BloodPressure)
            {
                BloodPressure bloodPressure =(BloodPressure) current;
                this.title1.setText("Sys:"+bloodPressure.getSystolic()+"Dias"+bloodPressure.getSystolic());
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date dateTime = bloodPressure.getMeasuredOn();
                Calendar calendarhere= Calendar.getInstance();
                calendarhere.set(Calendar.YEAR,dateTime.getYear());
                calendarhere.set(Calendar.MONTH,dateTime.getMonth());
                calendarhere.set(Calendar.DATE,dateTime.getDate());

                this.title2.setText(dateFormatter.format(calendarhere.getTime()));
                this.imgThumb.setImageResource(bloodPressure.getImageType());

            }

        }
    }
}
