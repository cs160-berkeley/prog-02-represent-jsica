package com.example.jessica.reps;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;

public class repview extends AppCompatActivity {

    ArrayList<rep> representatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String bool = intent.getStringExtra(title.NEXT_MESSAGE);
        String location = intent.getStringExtra(title.EXTRA_MESSAGE);
        String from = intent.getStringExtra("source");
        System.out.println("FROM :" + from);
        String rando = null;

        if ("REP_INFO".equals(from)) {
            Bundle b = intent.getExtras();
            rando = b.getString("REP_INFO");
            System.out.println("RANDO :" + rando);
        }

        if(bool == "true") {
            int zipcode = Integer.parseInt(location);

        } else {
            //location is my location, which is already a string; get the repview id of the location and change it
        }

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



        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("REP_NAME", representatives.get(1).n);
        sendIntent.putExtra("REP_VOTE", representatives.get(1).vote);
        sendIntent.putExtra("REP_PER", representatives.get(1).percent);
        sendIntent.putExtra("REP_PIC", representatives.get(1).picid);
        sendIntent.putExtra("REP_PART", representatives.get(1).party);
        sendIntent.putExtra("REP_PLACE", representatives.get(1).city);
        // get the info and send to watch
        startService(sendIntent);




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



//
//        TextView textView = (TextView) findViewById(R.id.textView3);
//
//        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Genome-Thin.otf");
//
//        textView.setTypeface(custom_font);
//        textView.setText("Hi, " + n1);
    }

}
