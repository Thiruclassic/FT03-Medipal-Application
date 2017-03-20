package iss.medipal.model;

import java.util.Date;

/**
 * Created by sreekumar  on 3/19/2017.
 */

public class Pulse extends Measurement{

    private int pulse;


    public Pulse(int id,int imageId,int pulse,Date measuredOn){

        super(id,imageId,measuredOn);
        this.pulse=pulse;

    }

    public Pulse(int id,int pulse,Date measuredOn){

        super(id,measuredOn);
        this.pulse=pulse;

    }

    public Pulse(){

    }
    public Pulse(Date measuredOn,int pulse){
        super(measuredOn);
        this.pulse = pulse;

    }


    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
}
