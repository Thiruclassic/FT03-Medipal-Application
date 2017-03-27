package iss.medipal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import iss.medipal.model.Measurement;

/**
 * Created by Thirumal on 26/3/2017.
 */

public class AllTests extends TestCase {

    public static Test suite()
    {
        TestSuite suite=new TestSuite("Test for Package "+MedipalUnitTest.context.getPackageName());
        suite.addTestSuite(MedicineUnitTest.class);
        suite.addTestSuite(ICEUnitTest.class);
        suite.addTestSuite(AppointmentUnitTest.class);
        suite.addTestSuite(MeasurementUnitTest.class);
        return suite;
    }
}
