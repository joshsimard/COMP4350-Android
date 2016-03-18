package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp4350.doctor_clientportal.R;

public class DoctorActivity extends AppCompatActivity {

    Button view_client_button;
    Button appointments_button;
    Button notes_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        intt();
        addActionListeners();
    }

    public void intt()
    {
        view_client_button = (Button)findViewById(R.id.edit_info_button);
        appointments_button = (Button)findViewById(R.id.appointment_button_cl);
        notes_button = (Button)findViewById(R.id.notes_button);
    }

    private void addActionListeners()
    {
        //view client button
        view_client_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, ClientListActivity2.class);
                startActivity(intent);
                //finish();
            }
        });

        //appointments button
        appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, CalanderActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //notes button
        notes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, NoteActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
