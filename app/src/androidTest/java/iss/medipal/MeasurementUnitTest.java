package iss.medipal;

import junit.framework.TestCase;

import java.util.List;

import iss.medipal.dao.MeasurementDao;
import iss.medipal.model.Measurement;

/**
 * Created by sreekumar on 3/26/2017.
 */

public class MeasurementUnitTest extends TestCase implements MedipalUnitTest {

    Measurement measurement;
    MeasurementDao measurementDao;
    List<Measurement> measurementList;

}
