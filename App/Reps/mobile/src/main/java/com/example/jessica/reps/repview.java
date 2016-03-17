package com.example.jessica.reps;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
//import io.fabric.sdk.android.Fabric;
//import com.twitter.sdk.android.Twitter;
//import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

//** Async code from Neil  http://stackoverflow.com/questions/30936865/how-make-sync-or-async-http-post-get-in-android-studio ** //

public class repview extends AppCompatActivity {

    ArrayList<rep> representatives;
    String SUN = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=";
    String sAPI = "&apikey=c77cecedde5d47c3821d7e22a51f1046";
    String billURL = "https://congress.api.sunlightfoundation.com/bills/search?sponsor_id=";
    String commURL = "https://congress.api.sunlightfoundation.com/committees?member_ids=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_repview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Intent intent = getIntent();
        String location = intent.getStringExtra(title.EXTRA_MESSAGE);
        String from = intent.getStringExtra("source");
        String lat = intent.getStringExtra("LAT");
        String lng = intent.getStringExtra("LNG");
        System.out.println("FROM :" + from);
        String rando = null;

        if ("REP_INFO".equals(from)) {
            Bundle b = intent.getExtras();
            rando = b.getString("REP_INFO");
            System.out.println("RANDO :" + rando);
        }

        TextView locText = (TextView) findViewById(R.id.place);
        locText.setText(location);
        populate();

        //Here make the call to sunlight
        String uRequest = SUN + lat + "&longitude=" + lng + sAPI;

        JSONObject repInfo = jparse(uRequest);
        ArrayList<rep> represents = updateReps(repInfo);

        if(represents.size() > 1) {
            representatives = represents;
        }



        if (rando != null) {
            int count = 0;
            for (rep r : representatives) {

                if (r.n == rando) {
                    rep temporary = representatives.get(1);
                    representatives.set(1, representatives.get(count));
                    representatives.set(count, temporary);

                }
                count += 1;
            }
        }

        changeUI();



        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("REP_NAME", representatives.get(1).n);
        sendIntent.putExtra("REP_VOTE", representatives.get(1).vote);
        sendIntent.putExtra("REP_PER", representatives.get(1).percent);
        sendIntent.putExtra("REP_PIC", representatives.get(1).picid);
        sendIntent.putExtra("REP_PART", representatives.get(1).party);
        sendIntent.putExtra("REP_PLACE", representatives.get(1).city);
        // get the info and send to watch
        startService(sendIntent);


    }

    public void changeUI() {


        final Button rFlip = (Button) findViewById(R.id.rightc);
        final Button lFlip = (Button) findViewById(R.id.leftc);
        final Button rBtn = (Button) findViewById(R.id.rightb);
        final Button lBtn = (Button) findViewById(R.id.leftb);
        final TextView party = (TextView) findViewById(R.id.party1);
        final TextView linkText = (TextView) findViewById(R.id.textView4);
        final TextView mailText = (TextView) findViewById(R.id.textView3);
        final TextView web = (TextView) findViewById(R.id.weblink);
        final TextView mail = (TextView) findViewById(R.id.email);
        final TextView nom = (TextView) findViewById(R.id.name1);

        //Flip Page
        final TextView tweetText = (TextView) findViewById(R.id.textView5);
        final TextView tweet = (TextView) findViewById(R.id.tweet);
        final TextView info = (TextView) findViewById(R.id.info);

        //Representative pics
        final ImageView rep1 = (ImageView) findViewById(R.id.lynn1);
        final int id1 = getResources().getIdentifier(representatives.get(1).picid, "drawable", getPackageName());


        final ImageView rep0 = (ImageView) findViewById(R.id.joe1);

        final ImageView rep2 = (ImageView) findViewById(R.id.pat1);

        rep curr = representatives.get(1);
        int pId = getResources().getIdentifier(curr.picid, "drawable", getPackageName());
        Drawable newPic = ContextCompat.getDrawable(getBaseContext(), pId);
        nom.setText(curr.n);
        party.setText(curr.party);
        rep1.setBackground(newPic);
        web.setText(curr.web);
        mail.setText(curr.mail);
        tweet.setText(curr.tweet);


        rFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rep temp = representatives.get(1);
                int id1 = getResources().getIdentifier(representatives.get(1).picid, "drawable", getPackageName());
                Drawable a = ContextCompat.getDrawable(getBaseContext(), id1);
                representatives.set(1, representatives.get(2));
                int id2 = getResources().getIdentifier(representatives.get(1).picid, "drawable", getPackageName());
                representatives.set(2, temp);
                Drawable b = ContextCompat.getDrawable(getBaseContext(), id2);

                rep curr = representatives.get(1);
                nom.setText(curr.n);
                party.setText(curr.party);
                rep1.setBackground(b);
                rep2.setBackground(a);
                web.setText(curr.web);
                mail.setText(curr.mail);
                tweet.setText(curr.tweet);

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("REP_NAME", representatives.get(1).n);
                sendIntent.putExtra("REP_VOTE", representatives.get(1).vote);
                sendIntent.putExtra("REP_PER", representatives.get(1).percent);
                sendIntent.putExtra("REP_PIC", representatives.get(1).picid);
                sendIntent.putExtra("REP_PART", representatives.get(1).party);
                sendIntent.putExtra("REP_PLACE", representatives.get(1).city);
                // get the info and send to watch
                startService(sendIntent);




            }
        });

        lFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rep temp = representatives.get(1);
                int id1 = getResources().getIdentifier(representatives.get(1).picid, "drawable", getPackageName());
                Drawable a = ContextCompat.getDrawable(getBaseContext(), id1);
                representatives.set(1, representatives.get(0));
                representatives.set(0, temp);
                int id2 = getResources().getIdentifier(representatives.get(1).picid, "drawable", getPackageName());
                Drawable b = ContextCompat.getDrawable(getBaseContext(), id2);

                rep curr = representatives.get(1);
                nom.setText(curr.n);
                party.setText(curr.party);
                rep1.setBackground(b);
                rep0.setBackground(a);
                web.setText(curr.web);
                mail.setText(curr.mail);
                tweet.setText(curr.tweet);

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("REP_NAME", representatives.get(1).n);
                sendIntent.putExtra("REP_VOTE", representatives.get(1).vote);
                sendIntent.putExtra("REP_PER", representatives.get(1).percent);
                sendIntent.putExtra("REP_PIC", representatives.get(1).picid);
                sendIntent.putExtra("REP_PART", representatives.get(1).party);
                sendIntent.putExtra("REP_PLACE", representatives.get(1).city);
                // get the info and send to watch
                startService(sendIntent);




            }
        });

        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                party.setVisibility(View.INVISIBLE);
                linkText.setVisibility(View.INVISIBLE);
                mailText.setVisibility(View.INVISIBLE);
                web.setVisibility(View.INVISIBLE);
                mail.setVisibility(View.INVISIBLE);
                rBtn.setVisibility(View.INVISIBLE);
                rBtn.setClickable(false);

                tweetText.setVisibility(View.VISIBLE);
                tweet.setVisibility(View.VISIBLE);
                info.setVisibility(View.VISIBLE);
                info.setClickable(true);
                lBtn.setVisibility(View.VISIBLE);
                lBtn.setClickable(true);

            }
        });

        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweetText.setVisibility(View.INVISIBLE);
                tweet.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                info.setClickable(false);
                lBtn.setVisibility(View.INVISIBLE);
                lBtn.setClickable(false);

                party.setVisibility(View.VISIBLE);
                linkText.setVisibility(View.VISIBLE);
                mailText.setVisibility(View.VISIBLE);
                web.setVisibility(View.VISIBLE);
                mail.setVisibility(View.VISIBLE);
                rBtn.setVisibility(View.VISIBLE);
                rBtn.setClickable(true);


            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detView = new Intent(getBaseContext(), detail.class);
                detView.putExtra("RNAME", representatives.get(1).n);
                detView.putExtra("RPARTY", representatives.get(1).party);
                detView.putExtra("RSTART", representatives.get(1).term);
                detView.putExtra("RPIC", representatives.get(1).picid);
                detView.putExtra("RCOMM", representatives.get(1).comm);
                detView.putExtra("RBILLS", representatives.get(1).bills);

                startActivity(detView);

            }
        });

    }

    public void populate() {

        String pTweet = "Donald Trump might win!";
        String pmail = "patrob@us.gov";
        String pweb = "patrob.house.gov";
        rep pat = new rep("Pat Rob", pmail, pweb, "Lenexa", pTweet, "pat2", "10-08-2010 to present", "Republican");
        pat.setVote("Republican");
        pat.setPercent("45");
        pat.setComm("World Potter's League");
        pat.setBills("KeyStone Pipeline");

        String lTweet = "Oh what nice weather we're having!";
        String lmail = "lynnjenkins@us.gov";
        String lweb = "lynnjenkins.house.gov";
        rep lynn = new rep("Lynn Jenkins", lmail, lweb, "Shawnee Mission", lTweet, "lynn", "01-08-2010 to present", "Republican");
        lynn.setVote("Republican");
        lynn.setPercent("51");
        lynn.setComm("GMOs R GR8, Kentucky Fried Chicken");
        lynn.setBills("KeyStone Pipeline, Donald Trump Cheer Squad");

        String jTweet = "I'm a great politician!";
        String jmail = "jmoran@us.gov";
        String jweb = "joemoran.house.gov";
        rep joe = new rep("Joe Moran", jmail, jweb, "Overland Park", jTweet, "moran", "01-10-2001 to present", "Republican");
        joe.setVote("Republican");
        joe.setPercent("43");
        joe.setComm("World Health, Legion of Boom");
        joe.setBills("KeyStone Pipeline, Women's Health");

        String bTweet = "#Yolo am I right?";
        String bmail = "bwesterman@us.gov";
        String bweb = "bwesterman.house.gov";
        rep bruce = new rep("Bruce Westerman", bmail, bweb, "Middle-of-nowhere", bTweet, "bruce", "01-10-2001 to present", "Republican");
        bruce.setVote("Republican");
        bruce.setPercent("20");
        bruce.setComm("World Health, Legion of Boom");
        bruce.setBills("KeyStone Pipeline, Women's Health");



        representatives = new ArrayList<>(Arrays.asList(joe, lynn, pat, bruce));

    }



    public JSONObject jparse(String mUrl) {

        try {
            URL url = new URL(mUrl);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setDoInput(true);
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }


            httpsURLConnection.connect();
            int mStatus = httpsURLConnection.getResponseCode();
            Log.d("DEBUG_TAG", "The response is: " + mStatus);

            InputStream loc = httpsURLConnection.getInputStream();


            BufferedReader streamReader = new BufferedReader(new InputStreamReader(loc, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            streamReader.close();

            String resp = responseStrBuilder.toString();
            JSONObject mObject = new JSONObject(resp);

            return mObject;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return new JSONObject();
    }


    public ArrayList<rep> updateReps(JSONObject info) {
        ArrayList<rep> newReps = new ArrayList<rep>();

        try {
            JSONArray res = info.getJSONArray("results");

            if (res.length() < 2) {
                if (res.length() == 0) {
                    return new ArrayList<rep>();
                } else {
                    JSONObject rec = res.getJSONObject(0);
                    String fn = rec.getString("first_name");
                    String ln = rec.getString("last_name");
                    String email = rec.getString("oc_email");
                    String web = rec.getString("website");
                    String twitterId = rec.getString("twitter_id");
                    String ts = rec.getString("term_start");
                    String te = rec.getString("term_end");
                    String party = rec.getString("party");
                    if (party.equals("D")) {
                        party = "Democrat";
                    } else {
                        party = "Republican";
                    }
                    String place = rec.getString("state");
                    String bioId = rec.getString("bioguide_id");
                    String bURL = billURL + bioId + sAPI;
                    String cURL = commURL + bioId + sAPI;
                    JSONArray b = jparse(bURL).getJSONArray("results");
                    JSONArray c = jparse(cURL).getJSONArray("results");
                    String comms = "";
                    String bills = "";
                    for (int j = 0; j < b.length(); ++j) {
                        String bs = b.getJSONObject(j).getString("bill_id");
                        bills = bills + bs + ", ";
                    }

                    for (int k = 0; k < c.length(); ++k) {
                        String cs = c.getJSONObject(k).getString("name");
                        comms = comms + cs + ", ";
                    }

                    String mTweet = "Donald Trump might win!";

                    rep newRep = new rep(fn + " " + ln, email, web, place, mTweet, "bruce", ts + " to " + te, party);
                    newRep.setVote(party);
                    newRep.setPercent("20");
                    newRep.setComm(comms);
                    newRep.setBills(bills);

                    representatives.set(1, newRep);
                    return representatives;
                }} else {


                for (int i = 0; i < res.length(); ++i) {
                    JSONObject rec = res.getJSONObject(i);
                    String fn = rec.getString("first_name");
                    String ln = rec.getString("last_name");
                    String email = rec.getString("oc_email");
                    String web = rec.getString("website");
                    String twitterId = rec.getString("twitter_id");
                    String ts = rec.getString("term_start");
                    String te = rec.getString("term_end");
                    String party = rec.getString("party");
                    if (party.equals("D")) {
                        party = "Democrat";
                    } else {
                        party = "Republican";
                    }
                    String place = rec.getString("state");
                    String bioId = rec.getString("bioguide_id");
                    String bURL = billURL + bioId + sAPI;
                    String cURL = commURL + bioId + sAPI;
                    JSONArray b = jparse(bURL).getJSONArray("results");
                    JSONArray c = jparse(cURL).getJSONArray("results");
                    String comms = "";
                    String bills = "";
                    for (int j = 0; j < b.length(); ++j) {
                        String bs = b.getJSONObject(j).getString("bill_id");
                        bills = bills + bs + ", ";
                    }

                    for (int k = 0; k < c.length(); ++k) {
                        String cs = c.getJSONObject(k).getString("name");
                        comms = comms + cs + ", ";
                    }

                    String mTweet = "Donald Trump might win!";

                    rep newRep = new rep(fn + " " + ln, email, web, place, mTweet, "bruce", ts + " to " + te, party);
                    newRep.setVote(party);
                    newRep.setPercent("20");
                    newRep.setComm(comms);
                    newRep.setBills(bills);
                    newReps.add(newRep);
                    }
                }
            return newReps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newReps;


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //do your request in here so that you don't interrupt the UI thread
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }


    private String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        int length = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("ASYNC_TAG", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is, length);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
        }
    }


}
