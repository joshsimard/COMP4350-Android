package comp4350.doctor_clientportal.objects;

/**
 * Created by joshsimard on 2016-03-16.
 */
public class MTerms {
    private String name;
    private String body;


    public MTerms(String name, String body)
    {
        this.name = name;
        this.body = body;

    }

    //getters

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    //setters

    public void setBody(String body) {
        this.body = body;
    }

    public void setName(String name) {
        this.name = name;
    }
}