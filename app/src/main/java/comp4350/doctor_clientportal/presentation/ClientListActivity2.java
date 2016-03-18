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
import comp4350.doctor_clientportal.objects.Client;

public class ClientListActivity2 extends AppCompatActivity {

    private ArrayList<Client> clientList;
    static final String STATE_EVENT_LIST = null;

    private ArrayList<Integer> selectedPositions;
    private ListView list;
    private View clientItemView;
    private String listResult;
    private ArrayAdapter<Client> clientArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list2);
        populateClientList();
        populateListView();
    }

    private void populateClientList()
    {
        clientList = new ArrayList<Client>();

        //listResult = accessCourses.getCourses(courseList);

        for(int i = 0; i < 5; i++)
        {
            clientList.add(new Client("Cancer Patient" + i + 1, "jane@yeloo.com", "419"));
        }

        selectedPositions = new ArrayList<Integer>();
        System.out.println("This is the size " + clientList.size());
        for(int i = 0; i < clientList.size(); i++)
            selectedPositions.add(0);
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
