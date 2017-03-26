package iss.medipal.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import iss.medipal.MediPalApplication;
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
    private Context context;

    public RecyclerAdapter(Context context, List<Measurement> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.measurementDao= MeasurementDaoImpl.newInstance(context);
        this.context=context;
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
        holder.setData(currentObj,context);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// button click event
                if (null != currentObj) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
                    builder.setCancelable(false);
                    builder.setTitle("WARNING");
                    builder.setMessage("Do you want to delete permanently ?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int arg1) {
                            Boolean message = measurementDao.deleteMeasurement(currentObj.getId());
                            if (message) {

                                mData.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mData.size());
                                notifyDataSetChanged();
                                Toast.makeText(context,"DELETED SUCCESSFULLY",Toast.LENGTH_SHORT).show() ;
                            }else{

                                Toast.makeText(context,"DELETION FAILED !TRY AGAIN !!",Toast.LENGTH_SHORT).show() ;
                            }
                            alert.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int arg1) {
                            alert.dismiss();
                        }
                    });
                    builder.show();

                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if(!mData.isEmpty())
            return mData.size();
        else
            return 0;
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

        public void setData(Measurement current,Context context) {

            if (current instanceof BloodPressure) {
                if (null != current) {
                    BloodPressure bloodPressure = (BloodPressure) current;
                    Integer systolic = bloodPressure.getSystolic();
                    Integer diastolic = bloodPressure.getDiastolic();

                    this.title1.setText("S :" + systolic + " mmHg" + " D: " + diastolic + " mmHg");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    Date dateTime = bloodPressure.getMeasuredOn();

                    this.title2.setText(dateFormatter.format(dateTime));
                    this.imgThumb.setImageResource(bloodPressure.getImageType());
                    if((systolic<=90) && (diastolic <=60)){
                        //Low blood pressure
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.blue_500));

                    }else if((systolic<=120) && (diastolic <=80)){

                        //Ideal blood pressure
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.green_A700));
                    }
                    else if((systolic<=140) && (diastolic <=90)){
                        //Pre high blood pressure
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.yellow_500));
                    }
                    else if((systolic<=190) && (diastolic <=100)){
                        //High blood pressure
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.red_500));
                    }

                }

            } else if (current instanceof Weight) {

                if (null != current) {
                    //Need to implement BMI calculator
                    Weight weight = (Weight) current;
                    this.title1.setText(" " + weight.getWeight() + "Kg");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    Date dateTime = weight.getMeasuredOn();
                    Integer weightCheck = weight.getWeight();
                    this.title2.setText(dateFormatter.format(dateTime));
                    this.imgThumb.setImageResource(weight.getImageType());

                    if(weightCheck<=30){

                        imgThumb.setColorFilter(context.getResources().getColor(R.color.blue_500));
                    }else if(weightCheck<=60 && weightCheck >30){

                        imgThumb.setColorFilter(context.getResources().getColor(R.color.green_A700));

                    }else if(weightCheck<=80 && weightCheck >60){

                        imgThumb.setColorFilter(context.getResources().getColor(R.color.yellow_500));

                    }else if(weightCheck<150 && weightCheck>80){

                        imgThumb.setColorFilter(context.getResources().getColor(R.color.red_500));
                    }


                }
            } else if (current instanceof Temperature) {
                if (null != current) {
                    Temperature temp = (Temperature) current;
                    Integer temperature = temp.getTemperature();
                    this.title1.setText(" " +temperature + " `C");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    Date dateTime = temp.getMeasuredOn();


                    this.title2.setText(dateFormatter.format(dateTime));
                    this.imgThumb.setImageResource(temp.getImageType());

                    if(temperature<37){
                        //Normal
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.blue_500));

                    }else if(temperature==38) {

                        //Ideal
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.green_A700));

                    }
                    else if(temperature>38)
                    {
                        //High
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.red_500));
                    }
                }

            } else if (current instanceof Pulse) {
                if (null != current) {
                    Pulse pulse = (Pulse) current;
                    Integer pulseRate = pulse.getPulse();
                    this.title1.setText(" " +pulseRate+ " bpm");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    Date dateTime = pulse.getMeasuredOn();

                    this.title2.setText(dateFormatter.format(dateTime));
                    this.imgThumb.setImageResource(pulse.getImageType());

                    if(pulseRate<=60){
                        //Normal
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.blue_500));

                    }else if(pulseRate>60 && pulseRate<100) {

                        //Ideal
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.green_A700));

                    }
                    else if(pulseRate>100)
                    {
                        //High
                        imgThumb.setColorFilter(context.getResources().getColor(R.color.red_500));
                    }
                }

            }

        }
    }
}
