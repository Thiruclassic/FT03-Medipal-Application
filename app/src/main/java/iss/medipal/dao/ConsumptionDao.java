package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Consumption;
import iss.medipal.model.HealthBio;
import iss.medipal.model.Medicine;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface ConsumptionDao {

    public long createConsumtion(Consumption consumption);
    public List<Consumption> getAllConsumptions();
    public void deleteConsumtion(Consumption consumtion);
    public void deleteConsumtionByMedId(int medId);
    public void clearTable();
    public void close();

}
