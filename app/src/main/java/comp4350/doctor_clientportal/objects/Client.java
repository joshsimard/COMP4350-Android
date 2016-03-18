package comp4350.doctor_clientportal.objects;

/**
 * Created by edmondcotterell on 2016-03-17.
 */


public class Client
{
    private String clientName;
    private String clientEmail;
    private String clientID;




    public Client(String clientName, String clientEmail, String clientID)
    {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientID = clientID;

    }

    //getters

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientID() {
        return clientID;
    }

    //setters

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}