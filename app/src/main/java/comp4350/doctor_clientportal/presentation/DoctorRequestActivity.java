package comp4350.doctor_clientportal.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
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

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.MedRequest;
import comp4350.doctor_clientportal.objects.Note;

public class DoctorRequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ArrayList<MedRequest> medRequestList;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View requestItemView;
    private String listResult;
    private ArrayAdapter<MedRequest> requestArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
    private View headerView;
    private NavigationView navigationView;

    private int admin = 1;
    private String userID;
    private String userName;
    private String userEmail;
    private View dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_client_rq_view).setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        dialogView = getLayoutInflater().inflate(R.layout.number_picker_view, null);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(4).setChecked(true);

        navigationView.getMenu().getItem(5).setVisible(false);
        navigationView.getMenu().getItem(6).setVisible(false);
        navigationView.getMenu().getItem(7).setVisible(false);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
        }

        initt();
        populateRequestList();
    }

    private void initt()
    {
        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);

        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);
    }

    private void populateRequestList()
    {
        medRequestList = new ArrayList<MedRequest>();
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "requests", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                medRequestList.add(new MedRequest
                                        (json_data.getString("name"),
                                                json_data.getString("quantity"),
                                                json_data.getString("created_at"),
                                                json_data.getString("status"),
                                                json_data.getString("notes"),
                                                json_data.getString("client")));

                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + medRequestList.size());
                            for(int i = 0; i < medRequestList.size(); i++)
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
                        Toast.makeText(DoctorRequestActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private class RequestArrayAdapter extends ArrayAdapter<MedRequest> {
        public RequestArrayAdapter() {
            super(DoctorRequestActivity.this, R.layout.custom_request_item, medRequestList);

        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {

            requestItemView = convertView;
            final String[] note = {""};

            if (requestItemView == null)
                requestItemView = getLayoutInflater().inflate(R.layout.custom_request_item, parent, false);

            MedRequest currRequest = medRequestList.get(position);
            TextView name_textview = (TextView) requestItemView.findViewById(R.id.request_name);
            name_textview.setText(currRequest.getName());

            final TextView quantity_textview = (TextView) requestItemView.findViewById(R.id.request_quantity);
            quantity_textview.setText(currRequest.getQuantity());

            TextView client_textview = (TextView) requestItemView.findViewById(R.id.client_name_rq);
            client_textview.setText(currRequest.getClientName());

            TextView date_textview = (TextView) requestItemView.findViewById(R.id.date_rq);
            date_textview.setText(currRequest.getDate());

            final ImageView add_note_button = (ImageView) requestItemView.findViewById(R.id.edit_request);

            Button accept_button = (Button) requestItemView.findViewById(R.id.accept_request_button);
            Button decline_button = (Button) requestItemView.findViewById(R.id.decline_request_button);


            //action listener for Views
            accept_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do stuff
                    Toast.makeText(DoctorRequestActivity.this, "Accept Stuff", Toast.LENGTH_LONG).show();

                    /*
                    *
                    * if(!note[0].equals(""))
                        Save Note
                      else
                        no Note

                    * Call This when you done
                    *
                    *
                    * requestArrayAdapter.remove(requestArrayAdapter.getItem(position));
                    * */
                }
            });

            decline_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do stuff
                    Toast.makeText(DoctorRequestActivity.this, "Decline Stuff", Toast.LENGTH_LONG).show();

                    /*
                    * Call This when you done
                    *
                    * requestArrayAdapter.remove(requestArrayAdapter.getItem(position));
                    * */
                }
            });

            //quantity action listener to edit quantity
            quantity_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(DoctorRequestActivity.this);
                    final NumberPicker numberPicker = (MaterialNumberPicker) dialogView.findViewById(R.id.number_picker);
                    numberPicker.setBackgroundColor(getResources().getColor(R.color.grey));

                    numberPicker.setValue(Integer.parseInt(quantity_textview.getText().toString()));
                    final int[] value = {0};

                    builder.setTitle("Edit Quantity").setView(dialogView)
                            .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    value[0] = numberPicker.getValue();
                                    quantity_textview.setText(value[0] + "");
                                }
                            }).setNegativeButton("CANCEL", null);


                    AlertDialog dialog = builder.create();
                    if (dialogView.getParent() != null)
                        ((ViewGroup) dialogView.getParent()).removeView(dialogView); // <- fix
                    dialog.show();

                }
            });


            //add note
            add_note_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View dialogView = getLayoutInflater().inflate(R.layout.text_edit_view, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(DoctorRequestActivity.this);
                    final TextView noteDialog = (TextView) dialogView.findViewById(R.id.note_view_rq);
                    noteDialog.setBackgroundColor(getResources().getColor(R.color.grey));

                    noteDialog.setText(note[0]);
                    builder.setTitle("Add Note").setView(dialogView)
                            .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    note[0] = noteDialog.getText().toString();

                                    //indicate whether note has been added or not
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        if(!note[0].trim().equals(""))
                                        {
                                            add_note_button.setImageResource(R.drawable.ic_message_white_24dp);
                                            add_note_button.setBackground(getResources().getDrawable(R.drawable.filled_oval));
                                        }
                                        else
                                        {
                                            add_note_button.setImageResource(R.drawable.ic_message_black_24dp);
                                            add_note_button.setBackground(getResources().getDrawable(R.drawable.myrect));
                                            note[0] = "";
                                        }
                                    }
                                }
                            }).setNegativeButton("CANCEL", null);


                    AlertDialog dialog = builder.create();
                    if (dialogView.getParent() != null)
                        ((ViewGroup) dialogView.getParent()).removeView(dialogView); // <- fix
                    dialog.show();

                }
            });

            return requestItemView;
        }
    }

    private void populateListView()
    {

        if(listResult == null)
        {

            requestArrayAdapter = new RequestArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.list_doctor_request);
            courseListView.setAdapter(requestArrayAdapter);
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
            Intent intent = new Intent(DoctorRequestActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_clients)
        {
            Intent intent = new Intent(DoctorRequestActivity.this, ClientListActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_notes)
        {
            Intent intent = new Intent(DoctorRequestActivity.this, NoteActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mdlist)
        {
            Intent intent = new Intent(DoctorRequestActivity.this, MedicationActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(DoctorRequestActivity.this, LoginActivity.class);
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
        navigationView.getMenu().getItem(4).setChecked(true);
    }
}
