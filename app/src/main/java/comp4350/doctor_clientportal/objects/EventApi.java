package comp4350.doctor_clientportal.objects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Handler;

import comp4350.doctor_clientportal.R;
import comp4350.doctor_clientportal.presentation.LoginActivity;

/**
 * Created by edmondcotterell on 2016-03-16.
 */
public class EventApi extends AsyncTask<Void, String, String> {

    private Exception exception;

    public final static String apiURL = "http://ec2-52-32-93-246.us-west-2.compute.amazonaws.com/api/";
    public final static String EXTRA_MESSAGE = "CalEvents";
    public String eventsResponse = "";
    private Context context;
    public android.os.Handler handler;

    //TextView uiUpdate = (TextView) findViewById(R.id.output);

    public EventApi(Context context, android.os.Handler handler){
        this.context=context;
        this.handler=handler;
    }

    protected void onPreExecute() {
        //progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");

    }

    protected String doInBackground(Void... urls) {

        HttpURLConnection urlConnection = null;
        // Do some validation here
        try {
            URL url = new URL(apiURL + "events");
            String auth = android.util.Base64.encodeToString(
                    ("john@doe.com" + ":" + "password").getBytes(),
                    android.util.Base64.NO_WRAP
            );

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.addRequestProperty("Authorization", "Basic " + auth);
                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public String getEventsResponse() {
        return eventsResponse;
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        //progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);

        //Intent intent = new Intent(context, LoginActivity.class);
       // intent.putExtra(EXTRA_MESSAGE, response);
        //Bundle extras = intent.getExtras();
        //extras.putString(EXTRA_MESSAGE, response);

//        try {
//            context.startActivity(intent);
//            ((Activity)context).finish();
//        }catch (Exception e)
//        {
//            Log.i("INFO-ERROR",e.getMessage());
//        }

        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        //LoginActivity.apiEvents = response;
       // eventsResponse = response;
        //responseView.setText(response);
//        super.onPostExecute(response);
//        Message message = new Message();
//        Bundle bundle = new Bundle();
//        bundle.putString("file", response);
//        message.setData(bundle);
//        handler.sendMessage(message);
    }
}