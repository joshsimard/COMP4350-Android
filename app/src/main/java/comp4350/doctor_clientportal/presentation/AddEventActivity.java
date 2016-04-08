package comp4350.doctor_clientportal.presentation;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import comp4350.doctor_clientportal.R;

public class AddEventActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    private Button save_button;
    private int set_hour;
    private int set_minute;

    private String setDay = "";
    private String setTime = "";
    private String setEndTime = "";
    private String dateData = "";

    Spinner date_spinner;
    Spinner time_spinner;
    TimePickerDialog timePickerDialog;
    //TimePickerDialog endTimePickerDialog;
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
        timePickerDialog = TimePickerDialog.newInstance(AddEventActivity.this, 0, 0, false);
        datePickerDialog = DatePickerDialog.newInstance(
                AddEventActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );


        //date spinner
        dateItems = new String[]{"Date:","Set Date"};
        dateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateItems);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner = (Spinner) findViewById(R.id.date_spinner);
        date_spinner.setAdapter(dateAdapter);


        //time spinner
        timeItems = new String[]{"Time:","Set Time"};
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

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                date_spinner.setSelection(0);
            }
        });

        //fix bug
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

        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                time_spinner.setSelection(0);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(attemptSave()) {
                    String event_id = new Date().getTime()+""; // -> event_id
                    String title = title_view.getText().toString(); // ->title
                    dateData = setDay+" "+setTime; // ->start & end time

                    //Toast.makeText(AddEventActivity.this, dateData, Toast.LENGTH_LONG).show();
                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    JSONObject postData = new JSONObject();
                    JSONObject data = new JSONObject();


                    try {
                        postData.put("event_id", event_id);
                        postData.put("title", title+" - "+userName);
                        postData.put("start_time", dateData);
                        postData.put("end_time", setDay+" "+setEndTime);
                        postData.put("client_id", userEmail);
                        postData.put("client_name", userName);

                        data.put("data", postData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
                            (Request.Method.POST, apiURL + "events", data, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("data");

                                        //after saving data
                                        Toast.makeText(AddEventActivity.this, "Saved!", Toast.LENGTH_LONG).show();
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    //uiUpdate.setText("Response: " + error.toString());
                                    Toast.makeText(AddEventActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                    // Add the request to the RequestQueue.
                    queue.add(jsObjRequest);
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
        String minute_ss = (minute + 30) % 60 + "";
        String hour_ss = hourOfDay+"";
        set_hour = hourOfDay;
        set_minute = minute;

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
            minute_s ="0" + minute_s;

        //format minute
        if(minute_ss.length() < 2)
            minute_ss ="0" + minute_ss;

        if(Integer.parseInt(minute_ss) < Integer.parseInt(minute_s))
            hour_ss = (hourOfDay + 1) % 24 + "";

        setTime = hourOfDay+":"+minute_s+":00 GMT+0000";
        setEndTime = hour_ss+":"+minute_ss+":00 GMT+0000";
        //Toast.makeText(AddEventActivity.this, setTime, Toast.LENGTH_LONG).show();

        //re-init adapter
        timeItems = new String[]{hour_s+":"+minute_s+""+am_pm,"Time:", "Set Time"};
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
        dateItems = new String[]{day_s+", "+month_s+" "+dayOfMonth,"Date:", "Set Date"};
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
        if (date_spinner.getSelectedItem().toString().equals("Date:") || date_spinner.getSelectedItem().toString().equals("Set Date"))
        {
            TextView textView = (TextView) date_spinner.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.error));
            attempt = false;
        }
        if (time_spinner.getSelectedItem().toString().equals("Time:") || time_spinner.getSelectedItem().toString().equals("Set Time") )
        {
            TextView textView = (TextView) time_spinner.getChildAt(0);
            textView.setTextColor(getResources().getColor(R.color.error));
            attempt = false;
        }
        else if (set_hour > 22)
        {
            new AlertDialog.Builder(AddEventActivity.this)
                        .setTitle("Error")
                        .setMessage("Please Set Appointments before 10:00PM")
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
                            }
                        })
                    .show();
            attempt = false;
        }

        return attempt;
    }
}
