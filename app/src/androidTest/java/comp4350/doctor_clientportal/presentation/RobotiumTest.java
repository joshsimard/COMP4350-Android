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

    //runs tests for the doctor
    public void testDoctor() {
        //get the email view
        solo.enterText((AutoCompleteTextView) solo.getView(R.id.email), "john@doe.com");

        //get the password
        solo.enterText((EditText) solo.getView(R.id.password), "password");

        //click login
        solo.clickOnButton("Sign In");







        //select button VIEWCLIENT
        solo.clickOnButton("View Client");

        //assert activity is ClientListActivity
        solo.waitForActivity(ClientListActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientListActivity.class);

        //click on text Jane Doe
        solo.clickOnText("Jane Doe");

        //assert activity is clientactivity
        solo.waitForActivity(ClientInfo.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientInfo.class);

        //back to doctor DoctorActivity
        solo.goBack();
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Appointments");

        //assert activity is ClientListActivity
        solo.waitForActivity(CalanderActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", CalanderActivity.class);

        //search for the text on screen
        solo.searchText("Seminar - John Doe");

        //go back to DoctorActivity
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Notes");

        //assert activity is ClientListActivity
        solo.waitForActivity(NoteActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", NoteActivity.class);

        //search for specific note on screen
        solo.searchText("I would rather die a meaningful death than to live a meaningless life.");

        //go back to DoctorActivity
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Medical Terms");

        //assert activity is ClientListActivity
        solo.waitForActivity(MedicalTermsActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", MedicalTermsActivity.class);

        //search for specific note on screen
        solo.searchText("Cancer");

        //go back to DoctorActivity
        solo.goBack();
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

        //get title

        //get date

        //get time

        //click Submit

        //verify added thing there

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

        //find Nigerian puss
        solo.searchText("Nigerian puss");

        //find Declined
        solo.searchText("Declined");

        //click note button
        solo.clickOnButton(R.id.edit_request_cl);

        //find Your problem is beyond me. ..I can't help you
        solo.searchText("Your problem is beyond me. ..I can't help you");

        //click Ok
        solo.clickOnButton("Ok");

        //click add button
        solo.clickOnButton(R.id.fab_request);

        //verify AddRequestActivity
        solo.waitForActivity(AddRequestActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", AddRequestActivity.class);

        //get Drug Name Weed

        //get quantity 10

        //click submit
        solo.clickOnButton("Submit");

        //find Pending
        solo.searchText("Pending");

        //click drawer
        swipeToRight();

        //click Logout
        solo.clickOnButton("Logout");
    }

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