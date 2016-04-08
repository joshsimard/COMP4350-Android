package comp4350.doctor_clientportal.objects;

/**
 * Created by joshsimard on 2016-03-16.
 */
public class Medication {
    private String name;
    private String quantity;


    public Medication(String name, String quantity)
    {
        this.name = name;
        this.quantity = quantity;

    }

    //getters

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    //setters

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
}