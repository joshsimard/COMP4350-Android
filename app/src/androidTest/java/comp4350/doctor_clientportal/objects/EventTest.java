package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;


public class EventTest extends TestCase
{
    Event event;
    public EventTest()
    {
        event = new Event("New", "today", "tomorrow");
    }

    public void testGet()
    {
        System.out.println("Starting tests for event get");

        assertEquals("John", event.getTitle());
        assertEquals("today", event.getStartTime());
        assertEquals("tomorrow", event.getEndTime());

        System.out.println("Test complete for event get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for event set");

        String newTitle = "new title";
        String newStart = "new start";
        String newEnd = "new end";

        event.setTitle(newTitle);
        event.setStartTime(newStart);
        event.setEndTime(newEnd);

        assertEquals("new title", event.getTitle());
        assertEquals("new start", event.getStartTime());
        assertEquals("new end", event.getEndTime());

        System.out.println("Test complete for event set");
    }
}