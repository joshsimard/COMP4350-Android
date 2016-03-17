package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import comp4350.doctor_clientportal.R;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    /**
     * Todo: Add a note to the database
     *  - need to check both fields are filled in
     *  - use API to submit a POST request
     *  - confirm or deny POST via Toast
     *  - redirect to the note page (on success); stay on page for failure
     */
}
