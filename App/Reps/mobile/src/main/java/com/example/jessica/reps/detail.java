package com.example.jessica.reps;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.TextView;

public class detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Drawable pic;
        final Bundle extras = intent.getExtras();
        String rname = extras.getString("RNAME");
        String rparty = extras.getString("RPARTY");
        String rstart = extras.getString("RSTART");
        String rId = extras.getString("RPIC");
        String rcomm = extras.getString("RCOMM");
        String rbill = extras.getString("RBILLS");



        final Button rightB = (Button) findViewById(R.id.rightb);
        final Button leftB = (Button) findViewById(R.id.leftb);

        final TextView n1 = (TextView) findViewById(R.id.repname);
        final TextView party = (TextView) findViewById(R.id.partaff);
        final TextView termText = (TextView) findViewById(R.id.textView2);
        final TextView term = (TextView) findViewById(R.id.termS);

        final TextView commText = (TextView) findViewById(R.id.commt);
        final TextView comm = (TextView) findViewById(R.id.comms);
        final TextView billText = (TextView) findViewById(R.id.billText);
        final TextView bills = (TextView) findViewById(R.id.bills);

        n1.setText(rname);
        party.setText(rparty);
        term.setText(rstart);
        comm.setText(rcomm);
        bills.setText(rbill);




        rightB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                party.setVisibility(View.INVISIBLE);
                termText.setVisibility(View.INVISIBLE);
                term.setVisibility(View.INVISIBLE);
                rightB.setClickable(false);
                rightB.setVisibility(View.INVISIBLE);

                commText.setVisibility(View.VISIBLE);
                comm.setVisibility(View.VISIBLE);
                billText.setVisibility(View.VISIBLE);
                bills.setVisibility(View.VISIBLE);
                leftB.setVisibility(View.VISIBLE);
                leftB.setClickable(true);



            }
        });

        leftB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                party.setVisibility(View.VISIBLE);
                termText.setVisibility(View.VISIBLE);
                term.setVisibility(View.VISIBLE);
                rightB.setClickable(true);
                rightB.setVisibility(View.VISIBLE);

                commText.setVisibility(View.INVISIBLE);
                comm.setVisibility(View.INVISIBLE);
                billText.setVisibility(View.INVISIBLE);
                bills.setVisibility(View.INVISIBLE);
                leftB.setVisibility(View.INVISIBLE);
                leftB.setClickable(false);




            }
        });

    }

}
