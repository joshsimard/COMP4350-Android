package comp4350.doctor_clientportal.presentation;

import android.test.ActivityInstrumentationTestCase2;
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

        //assert activity is doctor activity
        solo.waitForActivity(DoctorActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", DoctorActivity.class);

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

        //back to doctor home
        solo.goBack();
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Appointments");

        //assert activity is ClientListActivity
        solo.waitForActivity(CalanderActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", CalanderActivity.class);

        //search for the text on screen
        solo.searchText("Seminar - John Doe");

        //go back to home
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Notes");

        //assert activity is ClientListActivity
        solo.waitForActivity(NoteActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", NoteActivity.class);

        //search for specific note on screen
        solo.searchText("I would rather die a meaningful death than to live a meaningless life.");

        //go back to home
        solo.goBack();

        //select button VIEWCLIENT
        solo.clickOnButton("Medical Terms");

        //assert activity is ClientListActivity
        solo.waitForActivity(MedicalTermsActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", MedicalTermsActivity.class);

        //search for specific note on screen
        solo.searchText("Cancer");

        //go back to home
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

        //assert activity is client activity
        solo.waitForActivity(ClientActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", ClientActivity.class);

        //select button EDIT INFORMATION
        solo.clickOnButton("Edit Information");

        //assert activity is Edit Client Activity
        solo.waitForActivity(EditClientActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", EditClientActivity.class);

        //search for jane's default texts
        solo.searchText("Jane");
        solo.searchText("Doe");

        //back to client home
        solo.goBack();

        //select button EDIT INFORMATION
        solo.clickOnButton("Appointments");

        //assert activity is Edit Client Activity
        solo.waitForActivity(CalanderActivity.class, 2000);
        solo.assertCurrentActivity("wrong activity", CalanderActivity.class);

        //search for the text on screen
        solo.searchText("Checkup - Jane Doe");

        //go back to client home
        solo.goBack();
    }

    public void setUp() throws Exception {
        super.setUp();
        solo=new Solo(getInstrumentation(),getActivity());
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}