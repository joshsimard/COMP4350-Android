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
import comp4350.doctor_clientportal.objects.Note;

public class NoteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<Note> noteList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View notesItemView;
    private String listResult;
    private ArrayAdapter<Note> noteArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
    private View headerView;
    private NavigationView navigationView;

    private int admin = 1;
    private String userID;
    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_notes_view).setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setChecked(true);

        navigationView.getMenu().getItem(5).setVisible(false);
        navigationView.getMenu().getItem(6).setVisible(false);
        navigationView.getMenu().getItem(7).setVisible(false);
        navigationView.getMenu().getItem(8).setVisible(false);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, AddNoteActivity.class);
                intent.putExtra("user_id", userID); //pass id
                startActivity(intent);
            }
        });

        initt();
        //populateNoteList(); /**make sure this is not needed
    }

    private void initt()
    {
        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);

        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);
    }

    private void populateNoteList()
    {
        noteList = new ArrayList<Note>();
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "notes/"+ userID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                noteList.add(new Note(json_data.getString("subject"), json_data.getString("body")));

                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + noteList.size());
                            for(int i = 0; i < noteList.size(); i++)
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
                        Toast.makeText(NoteActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private class NoteArrayAdapter extends ArrayAdapter<Note>
    {
        public NoteArrayAdapter()
        {
            super(NoteActivity.this,R.layout.custom_notes_item, noteList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            notesItemView = convertView;
            if(notesItemView == null)
                notesItemView = getLayoutInflater().inflate(R.layout.custom_notes_item,parent, false);

            Note currNote = noteList.get(position);
            TextView subject_textview = (TextView) notesItemView.findViewById(R.id.subject_name);
            subject_textview.setText(currNote.getSubject());

            TextView body_textview = (TextView) notesItemView.findViewById(R.id.note_body);
            body_textview.setText(currNote.getBody());


            return notesItemView;
        }

    }

    private void populateListView()
    {

        if(listResult == null)
        {

            noteArrayAdapter = new NoteArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listNotes);
            courseListView.setAdapter(noteArrayAdapter);
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

        if (id == R.id.nav_appoint)
        {
            Intent intent = new Intent(NoteActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_clients)
        {
            Intent intent = new Intent(NoteActivity.this, ClientListActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mdlist)
        {
            Intent intent = new Intent(NoteActivity.this, MedicationActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_client_request)
        {
            Intent intent = new Intent(NoteActivity.this, DoctorRequestActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(NoteActivity.this, LoginActivity.class);
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
        navigationView.getMenu().getItem(2).setChecked(true);
        populateNoteList();
    }
}
