package iss.medipal.model;

import java.util.Date;

/**
 * Created by Thirumal on 2/20/2017.
 */

public class Consumption {

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
}
