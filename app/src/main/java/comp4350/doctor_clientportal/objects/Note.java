package comp4350.doctor_clientportal.objects;

/**
 * Created by joshsimard on 2016-03-16.
 */
public class Note {
    private String subject;
    private String body;


    public Note(String subject, String body)
    {
        this.subject = subject;
        this.body = body;

    }

    //getters

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    //setters

    public void setBody(String body) {
        this.body = body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}