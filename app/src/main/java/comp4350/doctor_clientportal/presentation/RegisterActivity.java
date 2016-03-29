package comp4350.doctor_clientportal.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.Client;

public class RegisterActivity extends AppCompatActivity {

    TextView fName_view;
    TextView lName_view;
    TextView email_view;
    TextView password_view;
    TextView passwword_cfm_view;
    Button register_button;

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initt();
        registerUser();
    }

    private void initt()
    {
        fName_view = (TextView)findViewById(R.id.client_fname_edit);
        lName_view = (TextView)findViewById(R.id.client_lname_edit);
        email_view = (TextView)findViewById(R.id.client_email_edit);
        password_view = (TextView)findViewById(R.id.password_view_rg);
        passwword_cfm_view = (TextView)findViewById(R.id.password_cfm_view_rg);
        register_button = (Button)findViewById(R.id.submit_button);
    }

    private void registerUser()
    {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(password_view.getText().toString().equalsIgnoreCase(passwword_cfm_view.getText().toString()))) {
                    Toast.makeText(RegisterActivity.this, "Password Mismatch", Toast.LENGTH_LONG).show();
                } else if (attemptRegister()) {
                    //create request queue
                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    JSONObject postData = new JSONObject();
                    JSONObject data = new JSONObject();

                    try {
                        postData.put("firstName", fName_view.getText().toString());
                        postData.put("lastName", lName_view.getText().toString());
                        postData.put("email", email_view.getText().toString());
                        postData.put("password", password_view.getText().toString());

                        data.put("data", postData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, apiURL + "register", data, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("data");

                                        Toast.makeText(RegisterActivity.this, jsonArray.getString(0), Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    //uiUpdate.setText("Response: " + error.toString());
                                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                    // Add the request to the RequestQueue.
                    queue.add(jsObjRequest);
                }
            }
        });
    }

    private boolean attemptRegister()
    {
        boolean attempt = true;
        if (fName_view.getText().toString().isEmpty())
        {
            fName_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (lName_view.getText().toString().isEmpty())
        {
            lName_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (email_view.getText().toString().isEmpty())
        {
            email_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (password_view.getText().toString().isEmpty())
        {
            password_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (passwword_cfm_view.getText().toString().isEmpty())
        {
            passwword_cfm_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }

        // Check for a valid email address.
        if (!(email_view.getText().toString().contains("@")))
        {
            email_view.setError(getString(R.string.error_invalid_email));
            attempt = false;
        }
        return attempt;
    }


}
