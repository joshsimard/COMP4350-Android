package comp4350.doctor_clientportal.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by johnlarmie on 2016-03-18.
 */
public class RunUnitTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object tests");
        suite.addTestSuite(ClientTest.class);
        suite.addTestSuite(NoteTest.class);
        suite.addTestSuite(MTermsTest.class);
        suite.addTestSuite(MedicationTest.class);
        suite.addTestSuite(EventTest.class);
        suite.addTestSuite(MedRequestTest.class);

        return suite;
    }

}
