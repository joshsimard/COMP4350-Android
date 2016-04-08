package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;


public class MedicationTest extends TestCase
{
    Medication meds;
    public MedicationTest()
    {
        meds = new Medication("Cialis", "5");
    }

    public void testGet()
    {
        System.out.println("Starting tests for medication get");

        assertEquals("Cialis", meds.getName());
        assertEquals("5", meds.getQuantity());

        System.out.println("Test complete for medication get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for medication set");

        String newName = "dangerous";
        String newQuantity = "7";

        meds.setName(newName);
        meds.setQuantity(newQuantity);

        assertEquals("dangerous", meds.getName());
        assertEquals("7", meds.getQuantity());

        System.out.println("Test complete for medication set");
    }
}