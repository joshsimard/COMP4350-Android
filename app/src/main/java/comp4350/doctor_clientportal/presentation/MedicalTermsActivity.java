package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import comp4350.doctor_clientportal.objects.MTerms;

public class MedicalTermsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<MTerms> termList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View termsItemView;
    private String listResult;
    private ArrayAdapter<MTerms> termsArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";

    private  View headerView;
    private int admin = 0;
    private String userID;
    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_medical_terms);
        setContentView(R.layout.activity_drawer_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_medical_view).setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(6).setChecked(true);

        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
            admin = bundle.getInt("admin");
        }

        intt();
        populateTermList();
    }

    public void intt()
    {
        //set profile info
        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);
        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);
    }

    private void populateTermList()
    {
        termList = new ArrayList<MTerms>();
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "terms", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                termList.add(new MTerms(json_data.getString("name"), json_data.getString("description")));

                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + termList.size());
                            for(int i = 0; i < termList.size(); i++)
                                selectedPositions.add(0);

                            populateListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(MedicalTermsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private class MTermsArrayAdapter extends ArrayAdapter<MTerms>
    {
        public MTermsArrayAdapter()
        {
            super(MedicalTermsActivity.this,R.layout.custom_terms_item, termList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            termsItemView = convertView;
            if(termsItemView == null)
                termsItemView = getLayoutInflater().inflate(R.layout.custom_terms_item,parent, false);

            MTerms currTerm = termList.get(position);
            TextView subject_textview = (TextView) termsItemView.findViewById(R.id.subject_name_mt);
            subject_textview.setText(currTerm.getName());

            TextView body_textview = (TextView) termsItemView.findViewById(R.id.term_body);
            body_textview.setText(currTerm.getBody());


            return termsItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            termsArrayAdapter = new MTermsArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listTerms);
            courseListView.setAdapter(termsArrayAdapter);
        }
        else
        {
            Log.i("ERROR", "nawa");
        }

    }

    private void defaultIntentMessage(Intent intent)
    {
        intent.putExtra("user_id", userID);
        intent.putExtra("user_name", userName);
        intent.putExtra("user_email", userEmail);
        intent.putExtra("admin", admin);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_appoint_cl)
        {
            Intent intent = new Intent(MedicalTermsActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_edit_cl)
        {
            Intent intent = new Intent(MedicalTermsActivity.this, EditClientActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(MedicalTermsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
