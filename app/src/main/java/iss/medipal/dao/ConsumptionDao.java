package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Consumption;
import iss.medipal.model.HealthBio;
import iss.medipal.model.Medicine;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface ConsumptionDao {

    public Medicine createConsumtion(Consumption consumption);

    public Consumption getConsumptionForMedicine(int medicineId);

    public List<Consumption> getAllConsumptions();

    public Medicine deleteConsumtion(Consumption consumtion);

    public List<Consumption> getAllConsumptionsForLastXDays(int days);

    public List<Consumption> getMissedConsumptions();
    public void close();

}
