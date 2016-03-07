package com.example.jessica.reps;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
public class repviewflip extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_repviewflip);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });

        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        String repname = extras.getString("REP_NAME");
        String repper = extras.getString("REP_PER");
        String repvote = extras.getString("REP_VOTE");
        String reploc = extras.getString("REP_LOC");

        final TextView cname = (TextView) findViewById(R.id.textView);
        final TextView party = (TextView) findViewById(R.id.vote);
        final TextView loc = (TextView) findViewById(R.id.loc);
        final TextView per = (TextView) findViewById(R.id.demp);
        final Button flip = (Button) findViewById(R.id.button);

        cname.setText(repname);
        party.setText(repvote);
        loc.setText(reploc);
        per.setText(repper + "%");


       party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });






    }
}
