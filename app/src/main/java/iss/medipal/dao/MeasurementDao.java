package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.BloodPressure;
import iss.medipal.model.Measurement;
import iss.medipal.model.Pulse;
import iss.medipal.model.Temperature;
import iss.medipal.model.Weight;


/**
 * Created by Sreekumar on 2/24/17.
 */

public interface MeasurementDao {

    public Measurement getMeasurement();

    public Measurement getMeasurement(int id);

    public List<Measurement> getMeasurements();

    public List<BloodPressure> getBloodPressureValues();

    public List<Weight> getWeightValues();
    public List<Temperature> getTempValues();
    public List<Pulse> getPulseValues();

    public int addBloodPressure(BloodPressure measurement);
    public int addWeight(Weight weight);
    public int addTemperature(Temperature temperature);
    public int addPulse(Pulse pulse);
    public Boolean deleteMeasurement(int measurementId);

    public void close();

}
