package iss.medipal.model;

import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Measurement {
    int id;
    int systolic;
    int diastolic;
    int pulse;
    Date temperature;
    int weight;
    Date measuredOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public Date getTemperature() {
        return temperature;
    }

    public void setTemperature(Date temperature) {
        this.temperature = temperature;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(Date measuredOn) {
        this.measuredOn = measuredOn;
    }
}
