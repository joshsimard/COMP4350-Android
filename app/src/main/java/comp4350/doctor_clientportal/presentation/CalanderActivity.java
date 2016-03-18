package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);

        populateEventList();
        populateListView();
    }

    private void populateEventList()
    {
        eventList = new ArrayList<Event>();

        //listResult = accessCourses.getCourses(courseList);

        for(int i = 0; i < 5; i++)
        {
            eventList.add(new Event("Cancer Patient"+i+1,"Today 5:00am","Tommorow 9:00 pm"));
        }

        selectedPositions = new ArrayList<Integer>();
        System.out.println("This is the size " + eventList.size());
        for(int i = 0; i < eventList.size(); i++)
            selectedPositions.add(0);
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
            TextView event_title_textview = (TextView)eventItemView.findViewById(R.id.client_name);
            event_title_textview.setText(currEven.getTitle());

            TextView event_start_textview = (TextView)eventItemView.findViewById(R.id.client_email);
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
