package comp4350.doctor_clientportal;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp4350.doctor_clientportal.objects.RunUnitTests;

public class RunAllTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        suite.addTest(RunUnitTests.suite());

        return suite;
    }

}
