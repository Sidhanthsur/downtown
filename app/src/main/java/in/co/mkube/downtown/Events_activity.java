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


public class Events_activity extends ActionBarActivity implements View.OnClickListener{

    private ProgressDialog mProgress;

    private String url = "http://mkube.co.in/downtown/api1.php";

    private static final String TagEventName = "event_name";
    private static final String TagDate = "date";
    private static final String TagVenue = "venue";
    private static final String TagFrom = "from";
    private static final String TagTo = "to";
    private String EventName = null;
    private String Desc = null;

    public int textv[]={R.id.info_text,R.id.info_textdet,R.id.info_text1,R.id.info_textdet1,R.id.info_text2,R.id.info_textdet2,R.id.info_text3,R.id.info_textdet3,R.id.info_text4,R.id.info_textdet4};
    public int cardtv[]={R.id.card_view_events,R.id.card_view_events1,R.id.card_view_events2,R.id.card_view_events3,R.id.card_view_events4};
    public TextView tv[];
    private TextView tv1;
    public CardView cv[];
    private RelativeLayout rl;
    int i=0;

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
        tv=new TextView[10];
        cv=new CardView[5];

        tv1=(TextView) findViewById(R.id.textview);
        tv1.setText("Events at "+EventName);
        rl=(RelativeLayout)findViewById(R.id.relativelayout);

        for(i=0;i<5;i++) {
            cv[i]=(CardView)findViewById(cardtv[i]);
            cv[i].setOnClickListener(this);
        }

        for(i=0;i<10;i++)
        {
            tv[i]=(TextView)findViewById(textv[i]);
            tv[i].setTypeface(font);
            //tv[i].setOnClickListener(this);
        }

        new GetEvent().execute();
    }

    public HashMap<String, String> info;
    public ArrayList<HashMap<String, String>> infolist;
public String s;
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Events_activity.this,GroundOverlayActivity.class);
        switch(v.getId())
        {
            case R.id.info_text:
                s="one";
                intent.putExtra("card num",s);
                break;
            case R.id.info_text1:
                s="two";
                intent.putExtra("card num",s);
                break;
            case R.id.info_text2:
                s="three";
                intent.putExtra("card num",s);
                break;
            case R.id.info_text3:
                s="four";
                intent.putExtra("card num",s);
                break;
            case R.id.info_text4:
                s="five";
                intent.putExtra("card num",s);
                break;

            case R.id.info_textdet:
                s="one";   intent.putExtra("card num",s);
                break;
            case R.id.info_textdet1:
                s="two";
                intent.putExtra("card num",s);
                break;
            case R.id.info_textdet2:
                s="three";
                intent.putExtra("card num",s);
                break;
            case R.id.info_textdet3:
                s="four";
                intent.putExtra("card num",s);
                break;
            case R.id.info_textdet4:
                s="five";
                intent.putExtra("card num",s);
                break;
            case R.id.card_view_events:
                s="one";
                intent.putExtra("card num",s);
                break;
            case R.id.card_view_events1:
                s="two";
                intent.putExtra("card num",s);
                break;
            case R.id.card_view_events2:
                s="three";
                intent.putExtra("card num",s);
                break;
            case R.id.card_view_events3:
                s="four";
                intent.putExtra("card num",s);
                break;
            case R.id.card_view_events4:
                s="five";
                intent.putExtra("card num",s);
                break;
        }

           startActivity(intent);

    }

    private class GetEvent extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgress = new ProgressDialog(Events_activity.this);
            mProgress.setMessage("Please wait...");
            mProgress.setCancelable(false);
            mProgress.show();
            rl.setVisibility(View.INVISIBLE);
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
            for(int i=0;i<10;i+=2) {
              int  j=i/2;
                tv[i].setText(infolist.get(j).get(TagEventName));
                s1 = infolist.get(j).get(TagDate);
                s2 = infolist.get(j).get(TagFrom);
                s3 = infolist.get(j).get(TagTo);
                s4 = infolist.get(j).get(TagVenue);
                s5 = "\n" + s1 + "\n" + s2 + " to " + s3 + "\n" + s4;
                Log.d("asd",s5);
                tv[i+1].setText(s5);
            }
            tv[1].setText("\nJan 31\n 9:00 a.m\n Venue: EG 30");
            tv[2].setText("StartupWeekend");
            tv[3].setText("\nJan 31\n 9:00 a.m\n Venue: Hall of 1960");
            tv[4].setText("Bio-mimi cry");
            tv[5].setText("\nJan 31\n 10:00 a.m\n Venue: EG 49");
            tv[6].setText("Reverse Engineering");
            tv[7].setText("\nJan 31\n 11:00 a.m\n Venue: Henry Maudsel Hall");
            tv[8].setText("Ez-System");
            tv[9].setText("\nJan 31\n 9:00 a.m\n Venue: Main Gallery");

        }
    }
}