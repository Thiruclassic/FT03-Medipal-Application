package iss.medipal.model;

import java.util.ArrayList;
import java.util.List;

import iss.medipal.R;

/**
 * Created by sreekumar on 3/18/2017.
 */

public class MeasurementType {

    private String type;

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    private int imageType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static List<MeasurementType> getData() {

        List<MeasurementType> data = new ArrayList<>();

        int[] images = {
                R.drawable.heart_pulse,
                R.drawable.pulse,
                R.drawable.oil_temperature,
                R.drawable.weight_kilogram
        };

        String[] Categories = {"Blood Pressure","Pulse","Temperature", "Weight"};

        for (int i = 0; i < images.length; i++) {

            MeasurementType current = new MeasurementType();
            current.setType(Categories[i]);
            current.setImageType(images[i]);
            data.add(current);
        }

        return data;
    }
}
