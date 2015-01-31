package in.co.mkube.downtown;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Events_activity extends ActionBarActivity {

    private ProgressDialog mProgress;

    private String url = "http://mkube.co.in/downtown/api1.php";

    private static final String TagEventName = "event_name";
    private static final String TagDate = "date";
    private static final String TagVenue = "venue";
    private static final String TagFrom = "from";
    private static final String TagTo = "to";
    private String EventName = null;
    private String Desc = null;

    public TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_activity);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006699")));
        info = new HashMap<String, String>();
        infolist = new ArrayList<HashMap<String, String>>();
        EventName = getIntent().getStringExtra("EventName");
        Desc = getIntent().getStringExtra("Desc");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        tv1 = (TextView) findViewById(R.id.info_text);
        tv2 = (TextView) findViewById(R.id.info_textdet);
        tv1.setTypeface(font);
        tv2.setTypeface(font);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Events_activity.this,GroundOverlayActivity.class);
                startActivity(intent);

            }
        });
        new GetEvent().execute();
    }

    public HashMap<String, String> info;
    public ArrayList<HashMap<String, String>> infolist;

    private class GetEvent extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgress = new ProgressDialog(Events_activity.this);
            mProgress.setMessage("Please wait...");
            mProgress.setCancelable(false);
            mProgress.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String eve = "event";
                    JSONArray event = jsonObj.getJSONArray(eve);

                    for (int i = 0; i < event.length(); i++) {
                        JSONObject c = event.getJSONObject(i);

                        String name = c.getString(TagEventName);
                        String date = c.getString(TagDate);
                        String from = c.getString(TagFrom);
                        String to = c.getString(TagTo);
                        String venue = c.getString(TagVenue);

                        info.put(TagEventName, name);
                        info.put(TagDate, date);
                        info.put(TagFrom, from);
                        info.put(TagTo, to);
                        info.put(TagVenue, venue);
                        infolist.add(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (mProgress.isShowing())
                mProgress.dismiss();
         tv1.setText(infolist.get(0).get(TagEventName));
            String s1 = infolist.get(0).get(TagDate);
            String s2 = infolist.get(0).get(TagFrom);
            String s3 = infolist.get(0).get(TagTo);
            String s4= infolist.get(0).get(TagVenue);
            String s5= s1+"\n"+s2+" to "+s3+"\n"+s4;
            tv1.setText(s5);
        }
    }
}