package comp4350.doctor_clientportal.presentation;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import comp4350.doctor_clientportal.R;

public class AddEventActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private Button save_button;
    private int curr_hour;
    private int curr_minute;

    private String setDay = "";
    private String setTime = "";
    private String dateData = "";

    Spinner date_spinner;
    Spinner time_spinner;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    TextView title_view;
    String[] timeItems;
    String[] dateItems;
    ArrayAdapter<String> timeAdapter;
    ArrayAdapter<String> dateAdapter;

    private String userID;
    private String userName;
    private String userEmail;
    private int admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userID =  bundle.getString("user_id");
            userName =  bundle.getString("user_name");
            userEmail =  bundle.getString("user_email");
            admin = bundle.getInt("admin");
        }
        initt();
        addActionListener();
    }

    private void initt()
    {
        Calendar now = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(AddEventActivity.this, curr_hour, curr_minute, false);
        datePickerDialog = DatePickerDialog.newInstance(
                AddEventActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //date spinner
        dateItems = new String[]{"None","Set Date"};
        dateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateItems);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner = (Spinner) findViewById(R.id.date_spinner);
        date_spinner.setAdapter(dateAdapter);


        //time spinner
        timeItems = new String[]{"None","Set Time"};
        timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeItems);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner = (Spinner) findViewById(R.id.time_spinner);
        time_spinner.setAdapter(timeAdapter);

        save_button = (Button)findViewById(R.id.save_appoint_button);
        title_view = (TextView)findViewById(R.id.appoint_title);
    }

    private void addActionListener()
    {
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Set Date")) {
                    // do your stuff
                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                }
            } // to close the onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Set Time")) {
                    // do your stuff
                    timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
                }
            } // to close the onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(attemptSave()) {
                    String event_id = new Date().getTime()+""; // -> event_id
                    String title = title_view.getText().toString(); // ->title
                    dateData = setDay+" "+setTime; // ->start & end time
                    //userName; ->client_name
                    //userEmail; -> client_id

                    Toast.makeText(AddEventActivity.this, event_id, Toast.LENGTH_LONG).show();
                    //finish(); -> if it saves without error
                }
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        String am_pm = "AM";
        String minute_s = minute+"";
        String hour_s = hourOfDay+"";


        //format hour
        if(hourOfDay >= 12) {
            am_pm = "PM";

            if(hourOfDay != 12)
                hour_s = (hourOfDay - 12)+"";
        }
        else if(hourOfDay == 0)
            hour_s = "12";

        //format minute
        if(minute_s.length() < 2)
            minute_s+="0";

        setTime = hourOfDay+":"+minute_s+":00 GMT+0000";
        //Toast.makeText(AddEventActivity.this, setTime, Toast.LENGTH_LONG).show();

        //re-init adapter
        timeItems = new String[]{hour_s+":"+minute_s+""+am_pm,"Morning", "AfterNoon", "Evening", "Set Time"};
        timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeItems);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //get data here and set spinner
        time_spinner.setAdapter(timeAdapter);
        time_spinner.setSelection(0);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        Date date = new Date(year, monthOfYear, dayOfMonth-1);
        String day_s =  (String) android.text.format.DateFormat.format("EEEE", date);//Thursday;
        String month_s =  (String) android.text.format.DateFormat.format("MMMM", date);

        setDay = (String) android.text.format.DateFormat.format("EEE", date);
        setDay+=" "+(String) android.text.format.DateFormat.format("MMM", date);
        setDay+=" "+dayOfMonth+" "+year;

        //Toast.makeText(AddEventActivity.this, setDay, Toast.LENGTH_LONG).show();

        //re-init adapter
        dateItems = new String[]{day_s+", "+month_s+" "+dayOfMonth,"Today", "Set Date"};
        dateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateItems);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(dateAdapter);

        //get data here and set spinner
        date_spinner.setAdapter(dateAdapter);
        date_spinner.setSelection(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean attemptSave()
    {
        boolean attempt = true;
        if (title_view.getText().toString().isEmpty())
        {
            title_view.setError(getString(R.string.error_field_required));
            attempt = false;
        }
        if (date_spinner.getSelectedItem().toString().equals("None"))
        {
            TextView textView = (TextView) date_spinner.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.error));
            attempt = false;
        }
        if (time_spinner.getSelectedItem().toString().equals("None"))
        {
            TextView textView = (TextView) time_spinner.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.error));
            attempt = false;
        }

        return attempt;
    }
}
