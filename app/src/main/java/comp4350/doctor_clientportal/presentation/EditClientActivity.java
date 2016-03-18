package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.Note;

public class EditClientActivity extends AppCompatActivity {

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
    private String clientID;
    RequestQueue queue;
    private TextView client_fname_textView;
    private TextView client_lname_textView;
    private TextView client_email_textView;
    private TextView client_dob_textView;
    private TextView client_height_textView;
    private TextView client_weight_textView;
    private TextView client_phone_textView;
    private TextView client_home_phone_textView;
    private TextView client_pobox_textView;
    private TextView client_city_textView ;
    private TextView client_postal_textView;
    private TextView client_state_textView;
    private TextView client_country_textView;
    private TextView client_occupation_textView;
    private TextView client_marital_textView;
    private TextView client_kin_textView;
    private RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            clientID =  bundle.getString("client_id");
        }
        queue = Volley.newRequestQueue(this);
        populateEditForm();
        submitButton();
    }

//    {
//        "data":{
//        "userid":"2",
//                "firstName":"Jane",
//                "lastName":"Doe",
//                "dob":"19300411",
//                "email":"jane@doe.com",
//                "gender":"female",
//                "height":150,
//                "weight":200,
//                "mobileNum":"1234567",
//                "homeNum":"987654",
//                "address":"234 mary lane",
//                "city":"winnipeg",
//                "postalCode":"R2M2T2",
//                "state":"Manitoba",
//                "country":"Canada",
//                "occupation":"student",
//                "maritalStatus":"Married",
//                "nextOfKin":"Brother"
//    }
//    }
    private void submitButton()
    {
        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject postData = new JSONObject();
                JSONObject data = new JSONObject();
                try {
                    postData.put("userid", clientID);
                    postData.put("firstName", client_fname_textView.getText().toString().replace("-", ""));
                    postData.put("lastName", client_lname_textView.getText().toString());
                    postData.put("dob",client_dob_textView.getText().toString());
                    postData.put("email", client_email_textView.getText().toString());

                    if((findViewById(R.id.male_radio_edit)).isSelected())
                        postData.put("gender", "Male");
                    else if ((findViewById(R.id.female_radio_edit)).isSelected())
                        postData.put("gender", "Female");
                    else
                        postData.put("gender", "");

                    postData.put("height", client_height_textView.getText().toString());
                    postData.put("weight", client_weight_textView.getText().toString());
                    postData.put("mobileNum", client_phone_textView.getText().toString());
                    postData.put("homeNum", client_home_phone_textView.getText().toString());
                    postData.put("address", client_postal_textView.getText().toString());
                    postData.put("city", client_city_textView.getText().toString());
                    postData.put("postalCode", client_postal_textView.getText().toString());
                    postData.put("state", client_state_textView.getText().toString());
                    postData.put("country", client_country_textView.getText().toString());
                    postData.put("occupation", client_occupation_textView.getText().toString());
                    postData.put("maritalStatus",client_marital_textView.getText().toString());
                    postData.put("nextOfKin", client_kin_textView.getText().toString());

                    data.put("data", postData);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, apiURL + "clients", data, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
//                                try {
//                                    //response
//                                    JSONObject jsonArray = response.getJSONObject("data");
//                                    //Toast.makeText(EditClientActivity.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                Toast.makeText(EditClientActivity.this, "Data saved", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Toast.makeText(EditClientActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                // Add the request to the RequestQueue.
                queue.add(jsObjRequest);

            }
        });
    }

    private void populateForm(JSONObject json_data)
    {

        try {
            client_fname_textView = (TextView)findViewById(R.id.client_fname_edit);
            client_fname_textView.setText(json_data.getString("firstName"));

            client_lname_textView = (TextView)findViewById(R.id.client_lname_edit);
            client_lname_textView.setText(json_data.getString("lastName"));

            client_email_textView = (TextView)findViewById(R.id.client_email_edit);
            client_email_textView.setText(json_data.getString("email"));
            client_email_textView.setEnabled(false);

            client_dob_textView = (TextView)findViewById(R.id.client_dob_edit);
            client_dob_textView.setText(json_data.getString("dob"));

            gender = (RadioGroup)findViewById(R.id.gender_radiogp);
            if(json_data.getString("gender").equalsIgnoreCase("male"))
                gender.check(R.id.male_radio_edit);
            else if(json_data.getString("gender").equalsIgnoreCase("female"))
                gender.check(R.id.female_radio_edit);

            client_height_textView = (TextView)findViewById(R.id.client_height_edit);
            client_height_textView.setText(json_data.getString("height"));

            client_weight_textView = (TextView)findViewById(R.id.client_weight_edit);
            client_weight_textView.setText(json_data.getString("weight"));

            client_phone_textView = (TextView)findViewById(R.id.client_phone_edit);
            client_phone_textView.setText(json_data.getString("mobileNum"));

            client_home_phone_textView = (TextView)findViewById(R.id.client_phone_edit);
            client_home_phone_textView.setText(json_data.getString("homeNum"));

            client_pobox_textView = (TextView)findViewById(R.id.client_pobox_edit);
            client_pobox_textView.setText(json_data.getString("address"));

            client_city_textView = (TextView)findViewById(R.id.client_city_edit);
            client_city_textView.setText(json_data.getString("city"));

            client_postal_textView = (TextView)findViewById(R.id.client_postal_edit);
            client_postal_textView.setText(json_data.getString("postalCode"));

            client_state_textView = (TextView)findViewById(R.id.client_province_edit);
            client_state_textView.setText(json_data.getString("state"));

            client_country_textView = (TextView)findViewById(R.id.client_country_edit);
            client_country_textView.setText(json_data.getString("country"));

            client_occupation_textView = (TextView)findViewById(R.id.client_occupation_edit);
            client_occupation_textView.setText(json_data.getString("occupation"));

            client_marital_textView = (TextView)findViewById(R.id.client_marital_edit);
            client_marital_textView.setText(json_data.getString("maritalStatus"));

            client_kin_textView = (TextView)findViewById(R.id.client_next_kin_edit);
            client_kin_textView.setText(json_data.getString("nextOfKin"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateEditForm()
    {
        //create request queue
        //final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "clients/"+clientID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json_data = response.getJSONObject("data");
                           populateForm(json_data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(EditClientActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }
}
