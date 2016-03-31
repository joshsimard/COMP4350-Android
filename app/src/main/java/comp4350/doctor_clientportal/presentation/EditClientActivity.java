package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class EditClientActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
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

    private NavigationView navigationView;
    private View headerView;
    private String userID;
    private String userName;
    private int admin = 0;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_edit_view).setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(5).setChecked(true);

        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
        }
        queue = Volley.newRequestQueue(this);
        initt();
        populateEditForm();
        submitButton();
    }

    private void initt()
    {
        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);

        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);
    }

    private void submitButton()
    {
        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject postData = new JSONObject();
                JSONObject data = new JSONObject();
                try {
                    postData.put("userid", userID);
                    postData.put("firstName", client_fname_textView.getText().toString().replace("-", ""));
                    postData.put("lastName", client_lname_textView.getText().toString());
                    postData.put("dob", client_dob_textView.getText().toString());
                    postData.put("email", client_email_textView.getText().toString());

                    if ((findViewById(R.id.male_radio_edit)).isSelected())
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
                    postData.put("maritalStatus", client_marital_textView.getText().toString());
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
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "clients/"+ userID, null, new Response.Listener<JSONObject>() {

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

    private void defaultIntentMessage(Intent intent)
    {
        intent.putExtra("user_id", userID);
        intent.putExtra("user_name", userName);
        intent.putExtra("user_email", userEmail);
        intent.putExtra("admin", admin);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_appoint_cl)
        {
            Intent intent = new Intent(EditClientActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mterms_cl)
        {
            Intent intent = new Intent(EditClientActivity.this, MedicalTermsActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(EditClientActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //set drawer item
        navigationView.getMenu().getItem(5).setChecked(true);
    }
}
