package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Consumption;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface ConsumptionDao {

    public Consumption getConsumptionForMedicine(int medicineId);

    public List<Consumption> getAllConsumptions();

    public List<Consumption> getAllConsumptionsForLastXDays(int days);

    public List<Consumption> getMissedConsumptions();
    public void close();

}
