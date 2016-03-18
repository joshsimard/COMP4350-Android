package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp4350.doctor_clientportal.R;

public class ClientActivity extends AppCompatActivity {

    Button edit_info_button;
    Button appointments_button;
    Button idk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        intt();
        addActionListeners();
    }

    public void intt()
    {
        edit_info_button = (Button)findViewById(R.id.edit_info_button);
        appointments_button = (Button)findViewById(R.id.appointment_button_cl);
        idk_button = (Button)findViewById(R.id.idk_button);
    }

    private void addActionListeners()
    {
        //view client button
        edit_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, EditClientActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //appointments button
        appointments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, CalanderActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //notes button
        idk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, NoteActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
