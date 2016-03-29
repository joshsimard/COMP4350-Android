package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import comp4350.doctor_clientportal.R;

public class DoctorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button view_client_button;
    Button appointments_button;
    Button notes_button;
    Button medlist_button;
    TextView profile_email;
    TextView email_textview;
    TextView username_textview;
    View headerView;

    private String doctorID;
    private String doctorName;
    private String doctorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            doctorID =  bundle.getString("doctor_id");
            doctorName =  bundle.getString("doctor_name");
            doctorEmail =  bundle.getString("doctor_email");
        }

        intt();
        addActionListeners();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void intt()
    {

        //initialize ui elements
        view_client_button = (Button)findViewById(R.id.edit_info_button);
        appointments_button = (Button)findViewById(R.id.appointment_button_cl);
        notes_button = (Button)findViewById(R.id.notes_button);
        medlist_button = (Button)findViewById(R.id.mterms_button);
        profile_email = (TextView)findViewById(R.id.profile_email);

        email_textview = (TextView)headerView.findViewById(R.id.profile_email);
        email_textview.setText(doctorEmail);

        username_textview = (TextView)headerView.findViewById(R.id.user_name);
        username_textview.setText(doctorName);

    }

    private void addActionListeners()
    {
        //view client button
        view_client_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, ClientListActivity.class);
                defaultIntentMessage(intent);
            }
        });

        //appointments button
        appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, CalanderActivity.class);
                defaultIntentMessage(intent);
            }
        });

        //notes button
        notes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, NoteActivity.class);
                defaultIntentMessage(intent);
            }
        });

        //mterms button
        medlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(DoctorActivity.this, MedicalTermsActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void defaultIntentMessage(Intent intent)
    {
        intent.putExtra("doctor_id", doctorID);
        intent.putExtra("doctor_name", doctorName);
        intent.putExtra("doctor_email", doctorEmail);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_clients)
        {
            Intent intent = new Intent(DoctorActivity.this, ClientListActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_appoint)
        {
            Intent intent = new Intent(DoctorActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_notes)
        {
            Intent intent = new Intent(DoctorActivity.this, NoteActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_terms)
        {
//            Intent intent = new Intent(DoctorActivity.this, MedicalTermsActivity.class);
//            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
