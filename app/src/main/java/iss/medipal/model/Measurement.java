package iss.medipal.model;

import java.util.Date;

/**
 * Created by Sreekumar on 3/19/2017.
 */

public class Measurement {

    private int id;
    private int imageType;
    private Date measuredOn;

    public Measurement(){


    }
    public Measurement(int ID,Date measuredOn){

        this.id=ID;
        this.measuredOn=measuredOn;

    }
    public Measurement(int iD,int imageType,Date measuredOn){
        this.id=iD;
        this.imageType=imageType;
        this.measuredOn=measuredOn;

    }

    public Measurement(Date measuredOn){

        this.measuredOn=measuredOn;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(Date measuredOn) {
        this.measuredOn = measuredOn;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }
}
