package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import comp4350.doctor_clientportal.objects.Event;

public class CalanderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<Event> eventList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View eventItemView;
    private String listResult;
    private ArrayAdapter<Event> eventArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

    View headerView;
    NavigationView navigationView;
    private String userID;
    private String userName;
    private String userEmail;
    private int admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_appoint_view).setVisibility(View.VISIBLE);
        
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
            admin = bundle.getInt("admin");
        }

        // set drawer "appointment" item to selected
        if(admin == 1)
            navigationView.getMenu().getItem(1).setChecked(true);
        else
            navigationView.getMenu().getItem(4).setChecked(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_cal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CalanderActivity.this, "Do Stuff!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CalanderActivity.this, AddEventActivity.class);
                defaultIntentMessage(intent);
            }
        });

        initt();
        populateEventList();
    }

    private void initt()
    {
        //init drawer
        if(admin == 1)
        {
            navigationView.getMenu().getItem(4).setVisible(false);
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
        }
        else
        {
            navigationView.getMenu().getItem(0).setVisible(false);
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
        }

        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);

        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);
    }

    private void populateEventList()
    {
        eventList = new ArrayList<Event>();
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "events", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                // Toast.makeText(ClientListActivity.this, json_data.getString("firstName"), Toast.LENGTH_LONG).show();
                                //clientList.add(new Client(json_data.getString("firstName") + " " + json_data.getString("lastName"), json_data.getString("email"), json_data.getString("id")));

                                eventList.add(new Event(json_data.getString("title"),json_data.getString("start_time").substring(0,23),json_data.getString("end_time").substring(0,23)));
//                                        Log.i("log_tag", "_id" + json_data.getInt("account") +
//                                                        ", mall_name" + json_data.getString("name") +
//                                                        ", location" + json_data.getString("number") +
//                                                        ", telephone" + json_data.getString("url") +
//                                                        ",----" + json_data.getString("balance") +
//                                                        ",----" + json_data.getString("credit") +
//                                                        ",----" + json_data.getString("displayName")
//                                        );
                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + eventList.size());
                            for(int i = 0; i < eventList.size(); i++)
                                selectedPositions.add(0);

                            //uiUpdate.setText("Response: " + response.getJSONArray("data"));
                            populateListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        //uiUpdate.setText("Response: " + error.toString());
                        Toast.makeText(CalanderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

        //listResult = accessCourses.getCourses(courseList);

//        for(int i = 0; i < 5; i++)
//        {
//            eventList.add(new Event("Cancer Patient"+i+1,"Today 5:00am","Tommorow 9:00 pm"));
//        }
//
//        selectedPositions = new ArrayList<Integer>();
//        System.out.println("This is the size " + eventList.size());
//        for(int i = 0; i < eventList.size(); i++)
//            selectedPositions.add(0);
    }

    private class EventArrayAdapter extends ArrayAdapter<Event>
    {
        public EventArrayAdapter()
        {
            super(CalanderActivity.this,R.layout.custom_event_item, eventList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            eventItemView = convertView;
            if(eventItemView == null)
                eventItemView = getLayoutInflater().inflate(R.layout.custom_event_item,parent, false);

            Event currEven = eventList.get(position);
            TextView event_title_textview = (TextView)eventItemView.findViewById(R.id.subject_name);
            event_title_textview.setText(currEven.getTitle());

            TextView event_start_textview = (TextView)eventItemView.findViewById(R.id.note_body);
            event_start_textview.setText(currEven.getStartTime());

            //TextView event_end_textview = (TextView)eventItemView.findViewById(R.id.event_end_time);
            //event_end_textview.setText(currEven.getClientID());

            return eventItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            eventArrayAdapter = new EventArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listAppoints);
            courseListView.setAdapter(eventArrayAdapter);
        }
        else
        {
            Log.i("ERROR","nawa");
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_clients)
        {
            Intent intent = new Intent(CalanderActivity.this, ClientListActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_notes)
        {
            Intent intent = new Intent(CalanderActivity.this, NoteActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mdlist)
        {
            Intent intent = new Intent(CalanderActivity.this, MedicationActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mterms_cl)
        {
            Intent intent = new Intent(CalanderActivity.this, MedicalTermsActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_edit_cl)
        {
            Intent intent = new Intent(CalanderActivity.this, EditClientActivity.class);
            defaultIntentMessage(intent);
        }
        if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(CalanderActivity.this, LoginActivity.class);
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

        //set drawer item on resume
        if(admin == 1)
            navigationView.getMenu().getItem(1).setChecked(true);
        else
            navigationView.getMenu().getItem(4).setChecked(true);
    }

}
