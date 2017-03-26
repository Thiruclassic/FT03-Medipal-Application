package iss.medipal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created by Thirumal on 26/3/2017.
 */

public class AllTests extends TestCase {

    public static Test suite()
    {
        TestSuite suite=new TestSuite("Test for Package "+MedipalUnitTest.context.getPackageName());
        suite.addTestSuite(MedicineUnitTest.class);
        suite.addTestSuite(ICEUnitTest.class);

        return suite;
    }
}
