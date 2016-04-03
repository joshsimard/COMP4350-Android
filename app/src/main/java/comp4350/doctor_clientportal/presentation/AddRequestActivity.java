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

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import comp4350.doctor_clientportal.R;

public class AddRequestActivity extends AppCompatActivity {

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

    TextView name_text_view;
    TextView quantity_text_view;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initt();
        addActionListener();
    }


    private void initt()
    {
        name_text_view = (TextView)findViewById(R.id.drug_name_edit);
        quantity_text_view = (TextView)findViewById(R.id.quantity_edit_cl);
        submit = (Button)findViewById(R.id.drug_submit);
    }

    private void addActionListener()
    {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                {
//                    "data":{
//                    "name": "New Drug",
//                            "client": "Jane Doe",
//                            "email": "jane@doe.com",
//                            "quantity": "5",
//                            "status": "pending",
//                            "notes": "none"
//                }
//                }
                if(attemptSave())
                {
//                    //do stuff here
                    String name = name_text_view.getText().toString();
                    String quantity = quantity_text_view.getText().toString();
//                    Toast.makeText(AddRequestActivity.this, "Do Stuff!", Toast.LENGTH_LONG).show();
//

                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    JSONObject postData = new JSONObject();
                    JSONObject data = new JSONObject();


                    try {
                        postData.put("name", name);
                        postData.put("client", "John doe");
                        postData.put("email", "John@doe.com");
                        postData.put("quantity", quantity);
                        postData.put("status", "pending");
                        postData.put("notes", "none");

                        data.put("data", postData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, apiURL + "requests", data, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("data");

                                        //after saving data
                                        Toast.makeText(AddRequestActivity.this, "Saved!", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(AddRequestActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
        if (name_text_view.getText().toString().isEmpty())
        {
            name_text_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (quantity_text_view.getText().toString().isEmpty())
        {
            quantity_text_view.setError(getString(R.string.error_field_required));
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