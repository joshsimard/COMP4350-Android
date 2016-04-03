package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import java.util.ArrayList;

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.MedRequest;

public class AddNoteActivity extends AppCompatActivity {

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

    TextView title_text_view;
    TextView body_text_view;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initt();
        addActionListener();
    }

    private void initt()
    {
        title_text_view = (TextView)findViewById(R.id.note_name);
        body_text_view = (TextView)findViewById(R.id.note_body);
        submit = (Button)findViewById(R.id.note_submit);
    }

    private void addActionListener()
    {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                {
//                    "data":{
//                    "doctor_id":1,
//                            "subject":"Large Growth Testicles",
//                            "body":"Client #23584 has a large growth which has resulted in big testicles"
//                }
//                }
                if(attemptSave())
                {
                   //do stuff here
                    String name = title_text_view.getText().toString();
                    String body = body_text_view.getText().toString();
//                    Toast.makeText(AddNoteActivity.this, "Do Stuff!", Toast.LENGTH_LONG).show();


                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    JSONObject postData = new JSONObject();
                    JSONObject data = new JSONObject();


                    try {
                        postData.put("doctor_id", 1);
                        postData.put("subject", name);
                        postData.put("body", body);

                        data.put("data", postData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, apiURL + "notes", data, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("data");

                                        //after saving data
                                        Toast.makeText(AddNoteActivity.this, "Saved!", Toast.LENGTH_LONG).show();
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    //uiUpdate.setText("Response: " + error.toString());
                                    Toast.makeText(AddNoteActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                    // Add the request to the RequestQueue.
                    queue.add(jsObjRequest);
                }
            }
        });


    }

    private boolean attemptSave()
    {
        boolean attempt = true;
        if (title_text_view.getText().toString().isEmpty())
        {
            title_text_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (body_text_view.getText().toString().isEmpty())
        {
            body_text_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }

        return attempt;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

