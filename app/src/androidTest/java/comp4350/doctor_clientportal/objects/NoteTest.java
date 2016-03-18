package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;

/**
 * Created by johnlarmie on 2016-03-18.
 */
public class NoteTest extends TestCase
{
    Note note;
    public NoteTest()
    {
        note = new Note("This is a test title.", "This is a test body.");
    }

    public void testGet()
    {
        System.out.println("Starting tests for note get");

        assertEquals("This is a test body.", note.getBody());
        assertEquals("This is a test title.", note.getSubject());

        System.out.println("Test complete for note get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for note set");

        String newBody = "new body";
        String newSubject = "new subject";

        note.setBody(newBody);
        note.setSubject(newSubject);

        assertEquals("new body", note.getBody());
        assertEquals("new subject", note.getSubject());

        System.out.println("Test complete for note set");
    }
}