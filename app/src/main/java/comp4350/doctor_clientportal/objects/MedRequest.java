package comp4350.doctor_clientportal.objects;

/**
 * Created by joshsimard on 2016-03-16.
 */
public class MedRequest {
    private int id;
    private String name;
    private String quantity;
    private String date;
    private String status;
    private String note;
    private String clientName;



    public MedRequest(int id, String name, String quantity, String date, String status, String note, String clientName)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.note = note;
        this.clientName = clientName;

    }



    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getClientName() {
        return clientName;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


}