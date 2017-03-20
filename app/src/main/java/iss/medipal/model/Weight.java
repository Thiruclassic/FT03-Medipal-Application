package iss.medipal.model;

import java.util.Date;

/**
 * Created by sreekumar on 3/19/2017.
 */

public class Weight extends Measurement {

    private int weight;

    public Weight(int id,int imageId,int weight,Date measuredOn){

        super(id,imageId,measuredOn);
        this.weight=weight;

    }

    public Weight(){

    }
    public Weight(int id,int weight,Date measuredOn){

        super(id,measuredOn);
        this.weight=weight;

    }
    public Weight(Date measuredOn, int weight){

        super(measuredOn);
        this.weight=weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
