package comp4350.doctor_clientportal.presentation;
import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.EventApi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button button;

    public final static String EXTRA_MESSAGE = "CalEvents";
    public static String apiEvents = "";
    private   android.os.Handler handler;

    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView uiUpdate = (TextView) findViewById(R.id.testText);


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Reading JSON
//        JSONObject json = new JSONObject(jsonString);
//        JSONArray jArray = json.getJSONArray("list");
//
//        System.out.println("*****JARRAY*****" + jArray.length());
//
//        for(int i=0; i<jArray.length(); i++){
//            JSONObject json_data = jArray.getJSONObject(i);
//
//            Log.i("log_tag", "_id" + json_data.getInt("account") +
//                            ", mall_name" + json_data.getString("name") +
//                            ", location" + json_data.getString("number") +
//                            ", telephone" + json_data.getString("url") +
//                            ",----" + json_data.getString("balance") +
//                            ",----" + json_data.getString("credit") +
//                            ",----" + json_data.getString("displayName")
//            );
//        }

        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();


                //EventApi runner = new EventApi(getApplicationContext(), new android.os.Handler());
                //String sleepTime = time.getText().toString();
                //runner.execute();

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, apiURL + "clients", null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    uiUpdate.setText("Response: " + response.getJSONArray("data"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                uiUpdate.setText("Response: " + error.toString());
                            }


//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            Map<String, String> headers = new HashMap<>();
//                            String credentials = "john@doe.com:password";
//                            String auth = "Basic "
//                                    + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            headers.put("Content-Type", "application/json");
//                            headers.put("Authorization", auth);
//                            return headers;
//                        }
                        });
                // Add the request to the RequestQueue.
                queue.add(jsObjRequest);


//                final String[] message = {""};
//                handler = new android.os.Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        message[0] = msg.getData().getString("file"); // You can change this according to your requirement.
//
//                    }
//                };
                //Intent intent = getIntent();
                //String message = apiEvents; //intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
                //handler.handleMessage(runner.handler.obtainMessage());
                //Toast.makeText(LoginActivity.this,message[0], Toast.LENGTH_LONG).show();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        button = (Button)findViewById(R.id .action_test);
        addListenerOnButton();
    }

    private void addListenerOnButton()
    {
        button = (Button) findViewById(R.id.action_test);

        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0)
            {
                displayClientListPage();
            }
        });
    }

    private void displayClientListPage()
    {
        final Context context = this;

        Intent intent = new Intent(context, ClientListActivity.class);
        Bundle b = new Bundle();
        b.putString("name","ClientList");
        intent.putExtras(b);
        startActivity(intent);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    public class EventApi extends AsyncTask<Void, String, String> {

        private Exception exception;


        public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

        public final static String EXTRA_MESSAGE = "CalEvents";
        public String eventsResponse = "";
        private Context context;
        public android.os.Handler handler;



        public EventApi(Context context, android.os.Handler handler){
            this.context=context;
            this.handler=handler;
        }

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");

        }

        protected String doInBackground(Void... urls) {



//            HttpURLConnection urlConnection = null;
//            // Do some validation here
//            try {
//                URL url = new URL(apiURL + "events");
//                String auth = android.util.Base64.encodeToString(
//                        ("john@doe.com" + ":" + "password").getBytes(),
//                        android.util.Base64.NO_WRAP
//                );
//
//                try {
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("GET");
//                    urlConnection.addRequestProperty("Authorization", "Basic " + auth);
//                    urlConnection.connect();
//                    InputStream stream = urlConnection.getInputStream();
//
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuilder stringBuilder = new StringBuilder();
//
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    bufferedReader.close();
//                    return stringBuilder.toString();
//                }
//                finally{
//                    urlConnection.disconnect();
//                }
//            }
//            catch(Exception e) {
//                Log.e("ERROR", e.getMessage(), e);
//                return null;
//            }
            return null;
        }

        public String getEventsResponse() {
            return eventsResponse;
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            //progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);

            //Intent intent = new Intent(context, LoginActivity.class);
            // intent.putExtra(EXTRA_MESSAGE, response);
            //Bundle extras = intent.getExtras();
            //extras.putString(EXTRA_MESSAGE, response);

//        try {
//            context.startActivity(intent);
//            ((Activity)context).finish();
//        }catch (Exception e)
//        {
//            Log.i("INFO-ERROR",e.getMessage());
//        }

            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

            //uiUpdate.setText("Output : "+response);
            //LoginActivity.apiEvents = response;
            // eventsResponse = response;
            //responseView.setText(response);
//        super.onPostExecute(response);
//        Message message = new Message();
//        Bundle bundle = new Bundle();
//        bundle.putString("file", response);
//        message.setData(bundle);
//        handler.sendMessage(message);
        }
    }

}
