package comp4350.doctor_clientportal.objects;

/**
 * Created by joshsimard on 2016-03-16.
 */
public class Note {
    private String title;
    private String description;
    private int nID;

    public Note()
    {
        nID = 0;
        title = null;
        description = null;
    }

    public Note(int nID, String title, String description)
    {
        this.nID = nID;
        this.title = title;
        this.description = description;
        if(this.title == null || this.title.equalsIgnoreCase(""))
        {
            this.title = "(Unknown)";
        }
        if(this.description == null || this.description.equalsIgnoreCase(""))
        {
            this.description = "(Unknown)";
        }
    }

    public boolean equalsForSearch(String noteTitle)
    {
        return title.equalsIgnoreCase(noteTitle);
    }

    public int getnID() {return nID;}

    public String getTitle()
    {
        return title;
    }


    public String getDescription()
    {
        return description;
    }

    public void setnID(int nID) {this.nID = nID;}

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description) {this.description = description;}

    public Note copy()
    {
        Note result = new Note();

        result.setnID(nID);
        result.setTitle(title);
        result.setDescription(description);
        return result;
    }
}