package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Measurement;


/**
 * Created by Thirumal on 2/24/17.
 */

public interface MeasurementDao {
    public Measurement getMeasurement();

    public Measurement getMeasurement(int id);

    public List<Measurement> getMeasurements();
}
