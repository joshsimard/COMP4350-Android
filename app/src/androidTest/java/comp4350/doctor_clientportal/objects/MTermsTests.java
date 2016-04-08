package comp4350.doctor_clientportal.objects;

import junit.framework.TestCase;


public class MTermsTest extends TestCase
{
    MTerms terms;
    public MTermsTest()
    {
        terms = new MTerms("Cancer", "Cancer is a group of diseases");
    }

    public void testGet()
    {
        System.out.println("Starting tests for mterms get");

        assertEquals("Cancer", terms.getName());
        assertEquals("Cancer is a group of diseases", client.getBody());

        System.out.println("Test complete for mterms get");
    }

    public void testSet()
    {
        System.out.println("Starting tests for mterms set");

        String newName = "desease";
        String newBody = "new description";

        terms.setName(newName);
        terms.setBody(newBody);

        assertEquals("desease", terms.getName());
        assertEquals("new description", terms.getBody());

        System.out.println("Test complete for mterms set");
    }
}