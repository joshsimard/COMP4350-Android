package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.objects.Event;

public class CalanderActivity extends AppCompatActivity {

    private ArrayList<Event> eventList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View eventItemView;
    private String listResult;
    private ArrayAdapter<Event> eventArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);

        populateEventList();
        //populateListView();
    }

    private void populateEventList()
    {
        eventList = new ArrayList<Event>();
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "events", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                // Toast.makeText(ClientListActivity.this, json_data.getString("firstName"), Toast.LENGTH_LONG).show();
                                //clientList.add(new Client(json_data.getString("firstName") + " " + json_data.getString("lastName"), json_data.getString("email"), json_data.getString("id")));

                                eventList.add(new Event(json_data.getString("title"),json_data.getString("start_time").substring(0,23),json_data.getString("end_time").substring(0,23)));
//                                        Log.i("log_tag", "_id" + json_data.getInt("account") +
//                                                        ", mall_name" + json_data.getString("name") +
//                                                        ", location" + json_data.getString("number") +
//                                                        ", telephone" + json_data.getString("url") +
//                                                        ",----" + json_data.getString("balance") +
//                                                        ",----" + json_data.getString("credit") +
//                                                        ",----" + json_data.getString("displayName")
//                                        );
                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + eventList.size());
                            for(int i = 0; i < eventList.size(); i++)
                                selectedPositions.add(0);

                            //uiUpdate.setText("Response: " + response.getJSONArray("data"));
                            populateListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        //uiUpdate.setText("Response: " + error.toString());
                        Toast.makeText(CalanderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

        //listResult = accessCourses.getCourses(courseList);

//        for(int i = 0; i < 5; i++)
//        {
//            eventList.add(new Event("Cancer Patient"+i+1,"Today 5:00am","Tommorow 9:00 pm"));
//        }
//
//        selectedPositions = new ArrayList<Integer>();
//        System.out.println("This is the size " + eventList.size());
//        for(int i = 0; i < eventList.size(); i++)
//            selectedPositions.add(0);
    }

    private class EventArrayAdapter extends ArrayAdapter<Event>
    {
        public EventArrayAdapter()
        {
            super(CalanderActivity.this,R.layout.custom_event_item, eventList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            eventItemView = convertView;
            if(eventItemView == null)
                eventItemView = getLayoutInflater().inflate(R.layout.custom_event_item,parent, false);

            Event currEven = eventList.get(position);
            TextView event_title_textview = (TextView)eventItemView.findViewById(R.id.subject_name);
            event_title_textview.setText(currEven.getTitle());

            TextView event_start_textview = (TextView)eventItemView.findViewById(R.id.notes_body);
            event_start_textview.setText(currEven.getStartTime());

            //TextView event_end_textview = (TextView)eventItemView.findViewById(R.id.event_end_time);
            //event_end_textview.setText(currEven.getClientID());

            return eventItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            eventArrayAdapter = new EventArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listCourses);
            courseListView.setAdapter(eventArrayAdapter);
        }
        else
        {
            Log.i("ERROR","nawa");
        }

    }

}
