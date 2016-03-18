package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;

/**
 * Created by johnlarmie on 2016-03-18.
 */
public class ClientTest extends TestCase
{
    Client client;
    public ClientTest()
    {
        client = new Client("John", "example@com.com", "0");
    }

    public void testGet()
    {
        System.out.println("Starting tests for client get");

        assertEquals("John", client.getClientName());
        assertEquals("example@com.com", client.getClientEmail());
        assertEquals("0", client.getClientID());

        System.out.println("Test complete for client get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for client set");

        String newName = "new name";
        String newEmail = "new email";
        String newID = "new id";

        client.setClientName(newName);
        client.setClientEmail(newEmail);
        client.setClientID(newID);

        assertEquals("new name", client.getClientName());
        assertEquals("new email", client.getClientEmail());
        assertEquals("new id", client.getClientID());

        System.out.println("Test complete for client set");
    }
}