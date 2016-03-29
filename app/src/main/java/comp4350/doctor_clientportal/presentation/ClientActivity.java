package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import comp4350.doctor_clientportal.R;

public class ClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button edit_info_button;
    Button appointments_button;
    Button mterms_button;

    private  View headerView;
    private int admin = 0;
    private String userID;
    private String userName;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_client);
        setContentView(R.layout.activity_doctor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //select which layout to display
        findViewById(R.id.include_file).setVisibility(View.GONE);
        findViewById(R.id.include_client_view).setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(5).setChecked(true);

        navigationView.getMenu().getItem(0).setVisible(false);
        navigationView.getMenu().getItem(1).setVisible(false);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
        }

        intt();
        addActionListeners();
    }

    public void intt()
    {
        //set profile info
        TextView email_textview = (TextView) headerView.findViewById(R.id.profile_email);
        email_textview.setText(userEmail);
        TextView username_textview = (TextView) headerView.findViewById(R.id.user_name);
        username_textview.setText(userName);

        //init UI elements
        edit_info_button = (Button)findViewById(R.id.edit_info_button_cl);
        appointments_button = (Button)findViewById(R.id.appointment_button_cl);
        mterms_button = (Button)findViewById(R.id.mterms_button_cl);
    }

    private void addActionListeners()
    {
        //view client button
        edit_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, EditClientActivity.class);
                defaultIntentMessage(intent);
                Toast.makeText(ClientActivity.this, "Edit", Toast.LENGTH_LONG).show();
            }
        });

        //appointments button
        appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, CalanderActivity.class);
                defaultIntentMessage(intent);
                Toast.makeText(ClientActivity.this, "Appointment", Toast.LENGTH_LONG).show();
            }
        });

        //mterms button
        mterms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, MedicalTermsActivity.class);
                startActivity(intent);
            }
        });
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
            Intent intent = new Intent(ClientActivity.this, CalanderActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_edit_cl)
        {
            Intent intent = new Intent(ClientActivity.this, EditClientActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_mterms_cl)
        {
            Intent intent = new Intent(ClientActivity.this, MedicalTermsActivity.class);
            defaultIntentMessage(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Intent intent = new Intent(ClientActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
