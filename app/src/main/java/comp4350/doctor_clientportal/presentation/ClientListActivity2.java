package comp4350.doctor_clientportal.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ClientListActivity2 extends AppCompatActivity {

    private ArrayList<Client> clientList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View clientItemView;
    private String listResult;
    private ArrayAdapter<Client> clientArrayAdapter;
    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String url = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list2);
        populateClientList();

    }

    private void populateClientList()
    {
        clientList = new ArrayList<Client>();
        //Reading JSON
//        JSONObject json = new JSONObject(jsonString);
//        JSONArray jArray = json.getJSONArray("list");
//
//        System.out.println("*****JARRAY*****" + jArray.length());
//
//        for(int i=0; i<jArray.length(); i++){
//            JSONObject json_data = jArray.getJSONObject(i);
//
//            Log.i("log_tag", "_id" + json_data.getInt("account") +
//                            ", mall_name" + json_data.getString("name") +
//                            ", location" + json_data.getString("number") +
//                            ", telephone" + json_data.getString("url") +
//                            ",----" + json_data.getString("balance") +
//                            ",----" + json_data.getString("credit") +
//                            ",----" + json_data.getString("displayName")
//            );
//        }

        //create request queue
        final RequestQueue queue = Volley.newRequestQueue(this);

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, apiURL + "clients", null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    for(int i=0; i<jsonArray.length(); i++){
                                        JSONObject json_data = jsonArray.getJSONObject(i);

                                       // Toast.makeText(ClientListActivity2.this, json_data.getString("firstName"), Toast.LENGTH_LONG).show();
                                        clientList.add(new Client(json_data.getString("firstName") + " " + json_data.getString("lastName"), json_data.getString("email"), json_data.getString("id")));

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
                                    System.out.println("This is the size " + clientList.size());
                                    for(int i = 0; i < clientList.size(); i++)
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
                                Toast.makeText(ClientListActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                // Add the request to the RequestQueue.
                queue.add(jsObjRequest);
           // }
        //});

        //listResult = accessCourses.getCourses(courseList);

//        for(int i = 0; i < 5; i++)
//        {
//            clientList.add(new Client("Cancer Patient" + i + 1, "jane@yeloo.com", "419"));
//        }


    }

    private class ClientArrayAdapter extends ArrayAdapter<Client>
    {
        public ClientArrayAdapter()
        {
            super(ClientListActivity2.this,R.layout.custom_client_item, clientList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            clientItemView = convertView;
            if(clientItemView == null)
                clientItemView = getLayoutInflater().inflate(R.layout.custom_client_item,parent, false);

            Client currClient = clientList.get(position);
            TextView client_name_textview = (TextView) clientItemView.findViewById(R.id.client_name);
            client_name_textview.setText(currClient.getClientName());

            TextView client_email_textview = (TextView) clientItemView.findViewById(R.id.client_email);
            client_email_textview.setText(currClient.getClientEmail());

            TextView client_id_textview = (TextView) clientItemView.findViewById(R.id.client_id);
            client_id_textview.setText(currClient.getClientID());


            return clientItemView;
        }

    }
    private void populateListView()
    {

        if(listResult == null)
        {

            clientArrayAdapter = new ClientArrayAdapter();

            ListView courseListView = (ListView)findViewById(R.id.listClients);
            courseListView.setAdapter(clientArrayAdapter);
        }
        else
        {
            Log.i("ERROR", "nawa");
        }

    }
}
