package iss.medipal.model.homeMedicineModels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import iss.medipal.model.Consumption;
import iss.medipal.util.AppHelper;

/**
 * Created by junaidramis on 21/3/17.
 */

public class ConsumptionDayModel {

    private ArrayList<Consumption> consumptions;
    Calendar date;

    public ConsumptionDayModel(Calendar date){
        this.date = date;
    }

    public ArrayList<Consumption> getConsumptions() {
        return consumptions;
    }

    public void setConsumptions(ArrayList<Consumption> consumptions) {
        this.consumptions = consumptions;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
