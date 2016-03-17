package com.example.jessica.reps;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.wearable.Wearable;
import android.content.Intent;


/**
 * Created by Jessica on 3/12/16.
 */
//The Google API Client
public class GPSActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private String latText;
    private String longText;
    LocationRequest mLocationRequest = new LocationRequest();
    Boolean mRequestingLocationUpdates;

//    public GPSActivity() {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location LastLocation = null;
        while (LastLocation == null) {
            LastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        if (LastLocation != null) {
            mRequestingLocationUpdates = true;
            createLocationRequest();
            if (mRequestingLocationUpdates) {
                System.out.println("OK");
                startLocationUpdates();
            }
            LastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

//            latText = String.valueOf(LastLocation.getLatitude());
//            longText = String.valueOf(LastLocation.getLongitude());
//
//            Intent in =new Intent(getBaseContext(), title.class);
//            in.putExtra("LAT", latText);
//            in.putExtra("LONG",longText);
//            setResult(2, in);
//            finish();

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Location lLocation = location;
        latText = String.valueOf(lLocation.getLatitude());
        longText = String.valueOf(lLocation.getLongitude());
        Intent in =new Intent(getBaseContext(), title.class);
        in.putExtra("LAT", latText);
        in.putExtra("LONG",longText);
        setResult(2, in);
        finish();

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    protected void createLocationRequest() {
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());


    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}

    public String getLat() {
        return latText;
    }

    public String getLong() {
        return longText;
    }

}


