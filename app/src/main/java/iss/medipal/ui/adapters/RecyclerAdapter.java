package iss.medipal.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
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
import iss.medipal.dao.MeasurementDao;
import iss.medipal.dao.impl.MeasurementDaoImpl;
import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.model.Pulse;
import iss.medipal.model.Temperature;
import iss.medipal.model.Weight;

/**
 * Created by Sreekumar on 3/17/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private List<Measurement> mData;
    private LayoutInflater mInflater;
    private MeasurementDao measurementDao;
    private  Measurement currentObj = null;

    public RecyclerAdapter(Context context, List<Measurement> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.measurementDao= MeasurementDaoImpl.newInstance(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        currentObj = mData.get(position);
        holder.setData(currentObj);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// button click event
                if (null != currentObj) {

                    Boolean message = measurementDao.deleteMeasurement(currentObj.getId());

                    if (message) {

                        Snackbar.make(v, "Item deleted successfully!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mData.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mData.size());
                        notifyDataSetChanged();

                    } else {

                        Snackbar.make(v, "Item deletion failed!!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title1, title2;
        ImageView imgThumb, imgDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.txv_row1);
            title2 = (TextView) itemView.findViewById(R.id.txv_row2);
            imgThumb = (ImageView) itemView.findViewById(R.id.img_row1);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_row_delete);

        }

        public void setData(Measurement current) {

            if (current instanceof BloodPressure) {
                if (null != current) {
                    BloodPressure bloodPressure = (BloodPressure) current;
                    this.title1.setText("S :" + bloodPressure.getSystolic() + " mmHg" + " D: " + bloodPressure.getSystolic() + " mmHg");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateTime = bloodPressure.getMeasuredOn();
                    Calendar calendarhere = Calendar.getInstance();
                    calendarhere.set(Calendar.YEAR, dateTime.getYear());
                    calendarhere.set(Calendar.MONTH, dateTime.getMonth());
                    calendarhere.set(Calendar.DATE, dateTime.getDate());

                    this.title2.setText(dateFormatter.format(calendarhere.getTime()));
                    this.imgThumb.setImageResource(bloodPressure.getImageType());
                }

            } else if (current instanceof Weight) {

                if (null != current) {

                    Weight weight = (Weight) current;
                    this.title1.setText(" " + weight.getWeight() + "Kg");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateTime = weight.getMeasuredOn();
                    Calendar calendarhere = Calendar.getInstance();
                    calendarhere.set(Calendar.YEAR, dateTime.getYear());
                    calendarhere.set(Calendar.MONTH, dateTime.getMonth());
                    calendarhere.set(Calendar.DATE, dateTime.getDate());

                    this.title2.setText(dateFormatter.format(calendarhere.getTime()));
                    this.imgThumb.setImageResource(weight.getImageType());
                }
            } else if (current instanceof Temperature) {
                if (null != current) {
                    Temperature temp = (Temperature) current;
                    this.title1.setText(" " + temp.getTemperature() + " `C");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateTime = temp.getMeasuredOn();
                    Calendar calendarhere = Calendar.getInstance();
                    calendarhere.set(Calendar.YEAR, dateTime.getYear());
                    calendarhere.set(Calendar.MONTH, dateTime.getMonth());
                    calendarhere.set(Calendar.DATE, dateTime.getDate());

                    this.title2.setText(dateFormatter.format(calendarhere.getTime()));
                    this.imgThumb.setImageResource(temp.getImageType());
                }

            } else if (current instanceof Pulse) {
                if (null != current) {
                    Pulse pulse = (Pulse) current;
                    this.title1.setText(" " + pulse.getPulse() + " bpm");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dateTime = pulse.getMeasuredOn();
                    Calendar calendarhere = Calendar.getInstance();
                    calendarhere.set(Calendar.YEAR, dateTime.getYear());
                    calendarhere.set(Calendar.MONTH, dateTime.getMonth());
                    calendarhere.set(Calendar.DATE, dateTime.getDate());

                    this.title2.setText(dateFormatter.format(calendarhere.getTime()));
                    this.imgThumb.setImageResource(pulse.getImageType());
                }

            }

        }
    }
}
