package comp4350.doctor_clientportal.objects;

/**
 * Created by edmondcotterell on 2016-03-17.
 */


import java.util.ArrayList;
import java.util.List;

public class Event
{
    private String title;
    private String startTime;
    private String endTime;




    public Event(String title, String start, String end)
    {
        this.title = title;
        this.startTime = start;
        this.endTime = end;

    }

    //getters

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    //setters

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}