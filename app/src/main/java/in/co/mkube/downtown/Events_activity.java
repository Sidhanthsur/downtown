package in.co.mkube.downtown;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

    public TextView tv[]=new TextView[10];
    private TextView tv1;
    private RelativeLayout rl;

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

        rl = (RelativeLayout) findViewById(R.id.relativelayout);

        tv1 = (TextView) findViewById(R.id.textview);
        tv1.setText("Todays's events at "+ EventName);

        tv[0] = (TextView) findViewById(R.id.info_text);
        tv[1] = (TextView) findViewById(R.id.info_textdet);
        tv[2] = (TextView) findViewById(R.id.info_text1);
        tv[3] = (TextView) findViewById(R.id.info_textdet1);
        tv[4] = (TextView) findViewById(R.id.info_text2);
        tv[5] = (TextView) findViewById(R.id.info_textdet2);
        tv[6] = (TextView) findViewById(R.id.info_text3);
        tv[7] = (TextView) findViewById(R.id.info_textdet3);
        tv[8] = (TextView) findViewById(R.id.info_text4);
        tv[9] = (TextView) findViewById(R.id.info_textdet4);

        tv[1].setTypeface(font);
        tv[2].setTypeface(font);

        CardView c1 = (CardView)findViewById(R.id.card_view_events);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Events_activity.this, GroundOverlayActivity.class);
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
            rl.setVisibility(View.INVISIBLE);
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
                    JSONArray event = jsonObj.getJSONArray("event");

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
                        Log.d("QWERTY",infolist.get(i).get(TagEventName));
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
            rl.setVisibility(View.VISIBLE);
            if (mProgress.isShowing())
                mProgress.dismiss();
            String s1,s2,s3,s4,s5;
            for(int i=0;i<9;i+=2) {
                tv[i].setText(">  "+infolist.get(i/2).get(TagEventName));
                s1 = infolist.get(i/2).get(TagDate);
                s2 = infolist.get(i/2).get(TagFrom);
                s3 = infolist.get(i/2).get(TagTo);
                s4 = infolist.get(i/2).get(TagVenue);
                s5 = "\n Date: " + s1 + "\n Time: " + s2 + " to " + s3 + "\n Venue: " + s4;
                Log.d("asd",infolist.get(3).get(TagEventName));
                tv[i+1].setText(s5);
            }
        }
    }
}