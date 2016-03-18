package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            clientID =  bundle.getString("client_id");
        }
        populateEditForm();
    }

    private void populateForm(JSONObject json_data)
    {

        try {
            TextView client_fname_textView = (TextView)findViewById(R.id.client_fname_edit);
            client_fname_textView.setText(json_data.getString("firstName"));

            TextView client_lname_textView = (TextView)findViewById(R.id.client_lname_edit);
            client_lname_textView.setText(json_data.getString("lastName"));

            TextView client_email_textView = (TextView)findViewById(R.id.client_email_edit);
            client_email_textView.setText(json_data.getString("email"));
            client_email_textView.setEnabled(false);

            TextView client_dob_textView = (TextView)findViewById(R.id.client_dob_edit);
            client_dob_textView.setText(json_data.getString("dob"));

            RadioGroup gender = (RadioGroup)findViewById(R.id.gender_radiogp);
            if(json_data.getString("gender").equalsIgnoreCase("male"))
                gender.check(R.id.male_radio_edit);
            else if(json_data.getString("gender").equalsIgnoreCase("female"))
                gender.check(R.id.female_radio_edit);

            TextView client_height_textView = (TextView)findViewById(R.id.client_height_edit);
            client_height_textView.setText(json_data.getString("height"));

            TextView client_weight_textView = (TextView)findViewById(R.id.client_weight_edit);
            client_weight_textView.setText(json_data.getString("weight"));

            TextView client_phone_textView = (TextView)findViewById(R.id.client_phone_edit);
            client_phone_textView.setText(json_data.getString("mobileNum"));

            TextView client_home_phone_textView = (TextView)findViewById(R.id.client_phone_edit);
            client_home_phone_textView.setText(json_data.getString("homeNum"));

            TextView client_pobox_textView = (TextView)findViewById(R.id.client_pobox_edit);
            client_pobox_textView.setText(json_data.getString("address"));

            TextView client_city_textView = (TextView)findViewById(R.id.client_city_edit);
            client_city_textView.setText(json_data.getString("city"));

            TextView client_postal_textView = (TextView)findViewById(R.id.client_postal_edit);
            client_postal_textView.setText(json_data.getString("postalCode"));

            TextView client_state_textView = (TextView)findViewById(R.id.client_province_edit);
            client_state_textView.setText(json_data.getString("state"));

            TextView client_country_textView = (TextView)findViewById(R.id.client_country_edit);
            client_country_textView.setText(json_data.getString("country"));

            TextView client_occupation_textView = (TextView)findViewById(R.id.client_occupation_edit);
            client_occupation_textView.setText(json_data.getString("occupation"));

            TextView client_marital_textView = (TextView)findViewById(R.id.client_marital_edit);
            client_marital_textView.setText(json_data.getString("maritalStatus"));

            TextView client_kin_textView = (TextView)findViewById(R.id.client_next_kin_edit);
            client_kin_textView.setText(json_data.getString("nextOfKin"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateEditForm()
    {
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

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
