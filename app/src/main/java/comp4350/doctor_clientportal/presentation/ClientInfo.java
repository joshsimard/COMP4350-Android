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

import org.json.JSONException;
import org.json.JSONObject;

import comp4350.doctor_clientportal.R;

public class ClientInfo extends AppCompatActivity {

    private String clientID;
    private String clientName;
    private String clientEmail;

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            clientID =  bundle.getString("client_id");
            clientName =  bundle.getString("client_name");
            clientEmail =  bundle.getString("client_email");
        }

        initt();
        populateEditForm();
    }

    public void initt()
    {
        TextView id_textView = (TextView)findViewById(R.id.client_id_info) ;
        id_textView.setText(clientID);

        TextView email_textView = (TextView)findViewById(R.id.client_email_info) ;
        email_textView.setText(clientEmail);

        TextView name_textView = (TextView)findViewById(R.id.client_name_info) ;
        name_textView.setText(clientName);
    }


    private void populateForm(JSONObject json_data)
    {
        String empty = "N/A";

        try {

            TextView client_dob_textView = (TextView)findViewById(R.id.client_dob_info);
            if(!json_data.getString("dob").trim().equalsIgnoreCase(""))
                client_dob_textView.setText(json_data.getString("dob"));
            else
                client_dob_textView.setText(empty);

            TextView client_gender_textView = (TextView)findViewById(R.id.client_gender_info);
            if(!json_data.getString("gender").trim().equalsIgnoreCase(""))
                client_gender_textView.setText(json_data.getString("gender"));
            else
                client_gender_textView.setText(empty);


            TextView client_height_textView = (TextView)findViewById(R.id.client_height_info);
            if(!json_data.getString("height").trim().equalsIgnoreCase(""))
                client_height_textView.setText(json_data.getString("height"));
            else
                client_height_textView.setText(empty);


            TextView client_weight_textView = (TextView)findViewById(R.id.client_weight_info);
            if(!json_data.getString("weight").trim().equalsIgnoreCase(""))
                client_weight_textView.setText(json_data.getString("weight"));
            else
                client_weight_textView.setText(empty);

            TextView client_occupation_textView = (TextView)findViewById(R.id.client_occupation_info);
            if(!json_data.getString("weight").trim().equalsIgnoreCase(""))
                client_occupation_textView.setText(json_data.getString("occupation"));
            else
                client_occupation_textView.setText(empty);

            TextView client_marital_textView = (TextView)findViewById(R.id.client_marital_info);
            if(!json_data.getString("maritalStatus").trim().equalsIgnoreCase(""))
                client_marital_textView.setText(json_data.getString("maritalStatus"));
            else
                client_marital_textView.setText(empty);

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
                        Toast.makeText(ClientInfo.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }
}
