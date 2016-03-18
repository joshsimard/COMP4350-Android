package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            TextView textView = (TextView)findViewById(R.id.client_fname_edit);
            textView.setText(json_data.getString("firstName"));

            textView = (TextView)findViewById(R.id.client_lname_edit);
            textView.setText(json_data.getString("lastName"));


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
                            Toast.makeText(EditClientActivity.this, json_data.getString("id"), Toast.LENGTH_LONG).show();

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
