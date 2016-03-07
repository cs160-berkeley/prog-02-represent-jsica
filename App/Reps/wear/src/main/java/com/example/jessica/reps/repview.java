package com.example.jessica.reps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.content.Context;

public class repview extends Activity {


    String[] info;
    private TextView mTextView;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ShakeDetector mShakeDetector;
//        SensorManager mSensorManager;
//        Sensor mAccelerometer;

        setContentView(R.layout.round_activity_repview);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String rinfo = extras.getString("REP_INFO");
        info = rinfo.split("\\;");
        final ImageButton flip = (ImageButton) findViewById(R.id.img);
        final TextView party = (TextView) findViewById(R.id.party);
        final TextView cname = (TextView) findViewById(R.id.caname);
        final Button randomize = (Button) findViewById(R.id.randomizer);

        int id = getResources().getIdentifier(info[3], "drawable", getPackageName());
        Drawable a = ContextCompat.getDrawable(getBaseContext(), id);
        cname.setText(info[0]);
        party.setText(info[1]);
        flip.setBackground(a);



        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getBaseContext(), repviewflip.class);
                inte.putExtra("REP_NAME", info[0]);
                inte.putExtra("REP_VOTE", info[1]);
                inte.putExtra("REP_PER", info[2]);
                inte.putExtra("REP_LOC", info[5]);
                startActivity(inte);
            }
        });

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhone.class);
                Intent repIntent = new Intent(getBaseContext(), random_rep.class);
                sendIntent.putExtra("REP_NAME", "Bruce Westerman");
                sendIntent.putExtra("REP_PLACE", "Arkansas");
                // get the info and send to watch
                startService(sendIntent);
                startActivity(repIntent);
            }
        });



        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new OnShakeListener() {
            @Override
            public void onShake() {
                // Do stuff!

                Intent sendIntent = new Intent(getBaseContext(), WatchToPhone.class);
                sendIntent.putExtra("REP_NAME", "Bruce Westerman");
                sendIntent.putExtra("REP_PLACE", "Arkansas");
                // get the info and send to watch
                startService(sendIntent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
