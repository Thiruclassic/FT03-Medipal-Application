package iss.medipal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sreekumar on 3/18/2017.
 */

public class MeasurementType {

    private String systolic;
    private String diastolic;
    private String measuredOn;

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    private int imageType;

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(String measuredOn) {
        this.measuredOn = measuredOn;
    }
}
