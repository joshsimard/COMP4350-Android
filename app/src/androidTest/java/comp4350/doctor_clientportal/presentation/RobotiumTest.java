package comp4350.doctor_clientportal.presentation;

import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.robotium.solo.Solo;

import comp4350.doctor_clientportal.R;

/**
 * To run robotium, rightclick on the test file and select run.
 *  (should run as a robotium test).
 *
 *  Tests were put in two methods as the teardown resets the app to the main login screen.
 *  We didn't want to have to login for each individual user story, so we did all user stories
 *  pertaining to a particular user type (client or doctor) in one method. Comments were added
 *  for easy clarification for each step of the robotium test run.
 */
public class RobotiumTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public RobotiumTest() {
        super(LoginActivity.class);
    }

    //runs tests for the client
    public void testClient() {
        //get the email view
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.email), "jane@doe.com");

        //get the password
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        //click login
        solo.clickOnButton("Sign In");

        /* ***************************************
         *          Appointments Tests
         * ***************************************/

        //verify on appointments page
        solo.waitForActivity(CalanderActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", CalanderActivity.class);

        //find Checkup - Jane Doe
        solo.searchText("Checkup - Jane Doe");

        //click add button
        solo.clickOnView(solo.getView(R.id.fab_cal));

        //assert activity is AddEventAcitivty
        solo.waitForActivity(AddEventActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", AddEventActivity.class);

        //get title
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.appoint_title), "Surgery - Jane Doe");

        //get date - click the 3 spots
        solo.clickOnText("Date");
        solo.clickOnText("Set Date");
        solo.clickOnText("OK");

        //get time - click the 3 spots
        solo.clickOnText("Time");
        solo.clickOnText("Set Time");
        solo.clickOnText("OK");

        //click Submit
        solo.clickOnButton("Submit");

        //verify added thing there
        solo.searchText("Surgery - Jane Doe");

        /* ***************************************
         *         Edit Information Tests
         * ***************************************/

        //click drawer
        swipeToRight();

        //click Edit Information
        solo.clickOnText("Edit Information");

        //assert activity is Edit Client Activity
        solo.waitForActivity(EditClientActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", EditClientActivity.class);

        //search for jane's default texts
        solo.searchText("Jane");
        solo.searchText("Doe");

        //add a phone number
        solo.enterText((EditText) solo.getView(R.id.client_phone_edit), "");
        solo.enterText((EditText) solo.getView(R.id.client_phone_edit), "(204)2039-283");

        //click Save
        solo.clickOnButton("Save");

        solo.waitForActivity(CalanderActivity.class, 2000);

        //click drawer
        swipeToRight();

        //click Edit Information
        solo.clickOnText("Edit Information");

        //check to see new phone number
        solo.searchText("(204)2039-283");

        /* ***************************************
         *          Medical Terms Tests
         * ***************************************/

        //click drawer
        swipeToRight();

        //click Medical Terms
        solo.clickOnText("Medical Terms");

        //verify medical terms
        solo.waitForActivity(MedicalTermsActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", MedicalTermsActivity.class);

        //find Morphine
        solo.searchText("Morphine");

        /* ***************************************
         *          Requests Tests
         * ***************************************/

        //click drawer
        swipeToRight();

        //click Requests
        solo.clickOnText("Requests");

        //verify requests
        solo.waitForActivity(ClientRequestActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientRequestActivity.class);

        //click add button
        solo.clickOnView(solo.getView(R.id.fab_request));

        //verify AddRequestActivity
        solo.waitForActivity(AddRequestActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", AddRequestActivity.class);

        //get Drug Name Weed
        solo.enterText((EditText) solo.getView(R.id.drug_name_edit), "Weed");

        //get quantity 10
        solo.enterText((EditText) solo.getView(R.id.quantity_edit_cl), "10");

        //click submit
        solo.clickOnButton("Submit");

        //find Pending
        solo.searchText("Pending");

        //click drawer
        swipeToRight();

        //click Logout
        solo.clickOnText("Logout");
    }

    //runs tests for the doctor
    public void testDoctor() {
        //get the email view
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.email), "john@doe.com");

        //get the password
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        //click login
        solo.clickOnButton("Sign In");

        /* ***************************************
         *          Client List Tests
         * ***************************************/

        //assert activity is ClientListActivity
        solo.waitForActivity(ClientListActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientListActivity.class);

        //click on text Jane Doe
        solo.clickOnText("Jane Doe");

        //assert activity is clientactivity
        solo.waitForActivity(ClientInfo.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientInfo.class);

        //check for some info
        solo.searchText("Jane Doe");
        solo.searchText("jane@doe.com");
        solo.searchText("170");
        solo.searchText("50");
        solo.searchText("female");

        //go back to Doctor home
        solo.goBack();

        //click drawer
        swipeToRight();

        //click Edit Information
        solo.clickOnText("Appointments");

        /* ***************************************
         *          Appointments Tests
         * ***************************************/

        //assert activity is Edit Client Activity
        solo.waitForActivity(CalanderActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", CalanderActivity.class);

        //click add button
        solo.clickOnView(solo.getView(R.id.fab_cal));

        //assert activity is AddEventAcitivty
        solo.waitForActivity(AddEventActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", AddEventActivity.class);

        //get title
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.appoint_title), "Cancer Surgery");

        //get date - click the 3 spots
        solo.clickOnText("Date");
        solo.clickOnText("Set Date");
        //solo.clickOnText("21");
        solo.clickOnText("OK");

        //get time - click the 3 spots
        solo.clickOnText("Time");
        solo.clickOnText("Set Time");
        //solo.clickOnText("1");
        solo.clickOnText("OK");

        //click Submit
        solo.clickOnButton("Submit");

        //verify added thing there
        solo.searchText("Cancer Surgery");

        //click drawer
        swipeToRight();

        //click Notes
        solo.clickOnText("Notes");

        /* ***************************************
         *          Notes Tests
         * ***************************************/

        //assert activity is Edit Client Activity
        solo.waitForActivity(NoteActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", NoteActivity.class);

        //search for the text on screen
        solo.searchText("Large Growth");

        //click on add notes
        solo.clickOnView(solo.getView(R.id.fab_note));

        //assert activity is Notes Activity
        solo.waitForActivity(AddNoteActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", AddNoteActivity.class);

        //enter title
        solo.enterText((EditText) solo.getView(R.id.note_name), "Test");

        //enter content
        solo.enterText((EditText) solo.getView(R.id.note_body), "This is a test");

        //click submit
        solo.clickOnButton("Submit");

        //assert activity is Edit Client Activity
        solo.waitForActivity(NoteActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", NoteActivity.class);

        //look for text of title
        solo.searchText("Test");

        //look for text of content
        solo.searchText("This is a test");

        //click drawer
        swipeToRight();

        //click Medication List
        solo.clickOnText("Medication List");

        /* ***************************************
         *          Medication List Tests
         * ***************************************/

        //assert activity is Medication List Activity
        solo.waitForActivity(MedicationActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", MedicationActivity.class);

        //search for the text on screen
        solo.searchText("Cialis");
        solo.searchText("10");

        //TODO: Do this
        //click on add med
        solo.clickOnView(solo.getView(R.id.fab_med));

        //assert activity is Order Med Activity
        solo.waitForActivity(OrderMedsActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", OrderMedsActivity.class);

        //enter medication name Weed
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.med_name_edit), "Weed");

        //select quantity 10
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.quantity_edit), "10");

        //click submit
        solo.clickOnButton("Submit");

        //assert activity is Medication Activity
        solo.waitForActivity(MedicationActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", MedicationActivity.class);

        //look for name
        solo.searchText("Weed");

        //look for quantity
        solo.searchText("10");

        //click drawer
        swipeToRight();

        //click Client Requests
        solo.clickOnText("Client Requests");

        /* ***************************************
         *          Client Requests Tests
         * ***************************************/

        //assert activity is CLient Requests
        solo.waitForActivity(DoctorRequestActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", DoctorRequestActivity.class);

        solo.clickOnButton("Accept");

        //click drawer
        swipeToRight();

        //click Logout
        solo.clickOnText("Logout");
    }

    //method to swipe right for the drawer menu
    private void swipeToRight() {
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0 ;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 1);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo=new Solo(getInstrumentation(),getActivity());
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}