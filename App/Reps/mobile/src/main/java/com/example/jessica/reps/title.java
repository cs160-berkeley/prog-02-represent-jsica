package com.example.jessica.reps;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.location.Geocoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import android.location.Address;

//import com.crashlytics.android.Crashlytics;
//import com.twitter.sdk.android.Twitter;
//import com.twitter.sdk.android.core.TwitterAuthConfig;
//import io.fabric.sdk.android.Fabric;


import org.json.JSONArray;
import org.json.JSONObject;


public class title extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String NEXT_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    private String GEOCODINGKEY = "&key=AIzaSyAQLOhJgGKzWr7tZIgOqrkxGtoGSnJ1UR0";
    private String REVERSE_GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private String ZIP_URL = "http://maps.googleapis.com/maps/api/geocode/json?address=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_title);
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

        EditText loc = (EditText) findViewById(R.id.location);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), GPSActivity.class);
                startActivityForResult(i, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        String latText = data.getStringExtra("LAT");
        String longText = data.getStringExtra("LONG");
        sActivity(latText, longText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void next(View view) {
        EditText zipcode = (EditText) findViewById(R.id.zip);
        String zip1 = zipcode.getText().toString();
        Boolean isInt = isInteger(zip1);
        if (zip1.length() == 5 && isInt) {

            try {
                String zUrl = ZIP_URL + zip1;
                JSONObject info = jparse(zUrl);

                JSONArray res = info.getJSONArray("results");

                JSONObject rec = res.getJSONObject(0);
                JSONObject geo = rec.getJSONObject("geometry");

                JSONObject loc = geo.getJSONObject("location");


                String lat = loc.getString("lat");
                String lngt = loc.getString("lng");
                sActivity(lat, lngt);



            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }




//        String loc1 = loc.getText().toString();
//


    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void sActivity(String lat, String longt) {



        try {

            String mUrl = REVERSE_GEOCODING_URL + lat + ","
                    + longt + GEOCODINGKEY;
//
//            URL url = new URL(mUrl);
//            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//            httpsURLConnection.setReadTimeout(10000);
//            httpsURLConnection.setConnectTimeout(15000);
//            httpsURLConnection.setRequestMethod("GET");
//            httpsURLConnection.setDoInput(true);
//            if (android.os.Build.VERSION.SDK_INT > 9)
//            {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }
//
//
//            httpsURLConnection.connect();
//            int mStatus = httpsURLConnection.getResponseCode();
//            Log.d("DEBUG_TAG", "The response is: " + mStatus);
////            JSONObject json = jParser.makeHttpRequest(url, "GET", param);
//
//            InputStream loc = httpsURLConnection.getInputStream();
//
//
//            BufferedReader streamReader = new BufferedReader(new InputStreamReader(loc, "UTF-8"));
//            StringBuilder responseStrBuilder = new StringBuilder();
//
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null) {
//                responseStrBuilder.append(inputStr);
//            }
//            streamReader.close();
//
//            String ok = responseStrBuilder.toString();
            JSONObject mObject = jparse(mUrl);

            JSONArray res = mObject.getJSONArray("results");
            String county = "Johnson County";

            for (int i = 0; i < res.length(); ++i) {
                JSONObject rec = res.getJSONObject(i);
                JSONArray comps = rec.getJSONArray("address_components");
                String n = rec.getJSONArray("types").getString(0);
                if (n.equals("administrative_area_level_2")) {
                    county = comps.getJSONObject(0).getString("long_name");
                    break;
                }

            }

            Intent intent = new Intent(this, repview.class);
            intent.putExtra(EXTRA_MESSAGE, county);
            intent.putExtra("LAT", lat);
            intent.putExtra("LNG", longt);

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();

        }

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
}