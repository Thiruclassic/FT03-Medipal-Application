package iss.medipal.model;

import android.support.annotation.NonNull;

import java.util.Date;

import iss.medipal.util.AppHelper;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Consumption implements Comparable<Consumption>{

    int id;
    int medicineId;
    int quantity;
    Date consumedOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getConsumedOn() {
        return consumedOn;
    }

    public void setConsumedOn(Date consumedOn) {
        this.consumedOn = consumedOn;
    }

    @Override
    public int compareTo(@NonNull Consumption o) {
        if(medicineId == o.medicineId && consumedOn.equals(o.consumedOn)){
            return 0;
        } else {
            return 1;
        }
    }
}
