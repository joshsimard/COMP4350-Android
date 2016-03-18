package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import comp4350.doctor_clientportal.R;

public class ClientInfo extends AppCompatActivity {

    private String clientID;
    private String clientName;
    private String clientEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            clientID =  bundle.getString("client_id");
            clientName =  bundle.getString("client_name");
            clientEmail =  bundle.getString("client_email");
        }

        initt();
    }

    public void initt()
    {
        TextView id_textView = (TextView)findViewById(R.id.client_id_info) ;
        id_textView.setText(clientID);

        TextView email_textView = (TextView)findViewById(R.id.client_email_info) ;
        email_textView.setText(clientEmail);

        TextView name_textView = (TextView)findViewById(R.id.client_name_info) ;
        name_textView.setText(clientName);
    }
}
