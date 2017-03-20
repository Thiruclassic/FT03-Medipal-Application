package iss.medipal.model;

import java.util.Date;

/**
 * Created by sreekumar on 3/19/2017.
 */

public class BloodPressure extends Measurement{


    private int systolic;
    private int diastolic;
    public BloodPressure(){

    }
    public BloodPressure(int ID,int systolic,int diastolic,Date measuredOn){

        super(ID,measuredOn);
        this.systolic=systolic;
        this.diastolic=diastolic;

    }
    public BloodPressure(int ID,int imageType,int systolic,int diastolic,Date measuredOn){

        super(ID,imageType,measuredOn);
        this.systolic=systolic;
        this.diastolic=diastolic;

    }

    public BloodPressure(int systolic,int diastolic,Date measuredOn){

        super(measuredOn);
        this.systolic=systolic;
        this.diastolic=diastolic;

    }
    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

}
