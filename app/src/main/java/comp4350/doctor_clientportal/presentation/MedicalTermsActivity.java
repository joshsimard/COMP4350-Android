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
import comp4350.doctor_clientportal.objects.MTerms;

public class MedicalTermsActivity extends AppCompatActivity {

    private ArrayList<MTerms> termList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View termsItemView;
    private String listResult;
    private ArrayAdapter<MTerms> termsArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_terms);
        populateTermList();
    }

    private void populateTermList()
    {
        termList = new ArrayList<MTerms>();
        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiURL + "terms", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject json_data = jsonArray.getJSONObject(i);

                                termList.add(new MTerms(json_data.getString("name"), json_data.getString("description")));

                            }
                            selectedPositions = new ArrayList<Integer>();
                            System.out.println("This is the size " + termList.size());
                            for(int i = 0; i < termList.size(); i++)
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
                        Toast.makeText(MedicalTermsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private class MTermsArrayAdapter extends ArrayAdapter<MTerms>
    {
        public MTermsArrayAdapter()
        {
            super(MedicalTermsActivity.this,R.layout.custom_terms_item, termList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            termsItemView = convertView;
            if(termsItemView == null)
                termsItemView = getLayoutInflater().inflate(R.layout.custom_terms_item,parent, false);

            MTerms currTerm = termList.get(position);
            TextView subject_textview = (TextView) termsItemView.findViewById(R.id.subject_name_mt);
            subject_textview.setText(currTerm.getName());

            TextView body_textview = (TextView) termsItemView.findViewById(R.id.term_body);
            body_textview.setText(currTerm.getBody());


            return termsItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            termsArrayAdapter = new MTermsArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listTerms);
            courseListView.setAdapter(termsArrayAdapter);
        }
        else
        {
            Log.i("ERROR", "nawa");
        }

    }
}
