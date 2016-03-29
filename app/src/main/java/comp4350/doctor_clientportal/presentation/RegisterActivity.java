package comp4350.doctor_clientportal.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import comp4350.doctor_clientportal.R;

public class RegisterActivity extends AppCompatActivity {

    TextView fName_view;
    TextView lName_view;
    TextView email_view;
    TextView password_view;
    TextView passwword_cfm_view;
    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initt();
        registerUser();
    }

    private void initt()
    {
        fName_view = (TextView)findViewById(R.id.client_fname_edit);
        lName_view = (TextView)findViewById(R.id.client_lname_edit);
        email_view = (TextView)findViewById(R.id.client_email_edit);
        password_view = (TextView)findViewById(R.id.password_view_rg);
        passwword_cfm_view = (TextView)findViewById(R.id.password_cfm_view_rg);
        register_button = (Button)findViewById(R.id.submit_button);
    }

    private void registerUser()
    {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DO SHIT
            }
        });
    }


}
