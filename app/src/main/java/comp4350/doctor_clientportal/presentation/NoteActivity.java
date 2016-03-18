package comp4350.doctor_clientportal.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import comp4350.doctor_clientportal.objects.Client;
import comp4350.doctor_clientportal.objects.Note;

public class NoteActivity extends AppCompatActivity {

    private ArrayList<Note> noteList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View notesItemView;
    private String listResult;
    private ArrayAdapter<Note> noteArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";

    private String doctorID;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        bundle = getIntent().getExtras();
        if(bundle != null) {
            doctorID =  bundle.getString("doctor_id");
        }

        populateClientList();
    }

    private void populateClientList()
    {
        noteList = new ArrayList<Note>();
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "notes/"+doctorID, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                noteList.add(new Note(json_data.getString("subject"), json_data.getString("body")));

                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + noteList.size());
                            for(int i = 0; i < noteList.size(); i++)
                                selectedPositions.add(0);

                            populateListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(NoteActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private class NoteArrayAdapter extends ArrayAdapter<Note>
    {
        public NoteArrayAdapter()
        {
            super(NoteActivity.this,R.layout.custom_notes_item, noteList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            notesItemView = convertView;
            if(notesItemView == null)
                notesItemView = getLayoutInflater().inflate(R.layout.custom_notes_item,parent, false);

            Note currNote = noteList.get(position);
            TextView subject_textview = (TextView) notesItemView.findViewById(R.id.subject_name);
            subject_textview.setText(currNote.getSubject());

            TextView body_textview = (TextView) notesItemView.findViewById(R.id.notes_body);
            body_textview.setText(currNote.getBody());


            return notesItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            noteArrayAdapter = new NoteArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listNotes);
            courseListView.setAdapter(noteArrayAdapter);
        }
        else
        {
            Log.i("ERROR", "nawa");
        }

    }
}
