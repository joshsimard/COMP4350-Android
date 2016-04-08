package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;


public class MedRequestTest extends TestCase
{
    MedRequest meds;
    public MedRequestTest()
    {
        meds = new MedRequest(1, "Cialis", "2", "today", "pending", "", "Jane");
    }

    public void testGet()
    {
        System.out.println("Starting tests for medrequest get");

        assertEquals(1, meds.getId());
        assertEquals("Cialis", meds.getName());
        assertEquals("2", meds.getQuantity());
        assertEquals("today", meds.getDate());
        assertEquals("pending", meds.getStatus());
        assertEquals("", meds.getNote());
        assertEquals("Jane", meds.getClientName());

        System.out.println("Test complete for medrequest get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for medrequest set");

        String newName = "new name";
        String newQuantity = "5";
        String newDate = "new date";
        String newStatus = "approved";
        String newNote = "new";
        String newClient = "John";


        meds.setId(2);
        meds.setName(newName);
        meds.setQuantity(newQuantity);
        meds.setDate(newDate);
        meds.setStatus(newStatus);
        meds.setNote(newNote);
        meds.setClient(newClient);

        assertEquals(2, meds.getId());
        assertEquals("new name", meds.getName());
        assertEquals("5", meds.getQuantity());
        assertEquals("new date", meds.getDate());
        assertEquals("approved", meds.getStatus());
        assertEquals("new", meds.getNote());
        assertEquals("John", meds.getClientName());

        System.out.println("Test complete for medrequest set");
    }
}