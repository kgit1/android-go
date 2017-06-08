package com.example.a.appubr;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    List<Location> ridersLocations;
    List<String> distanceToRiders;
    Location driversLocation;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        distanceToRiders = new ArrayList<>();
        ridersLocations = fillRidersLocations();


        initializeLocationManager();
        initializeLocationListener();


        driversLocation = lastKnownLocation();
        Log.i("Info","ridersLocation 2" + ridersLocations);

        for(Location location : ridersLocations){
            Log.i("Info","distance");
            Log.i("Info","di"+location.distanceTo(driversLocation) + "km");
            distanceToRiders.add(location.distanceTo(driversLocation) + "km");

        }

        ListView listViewRiders = (ListView)findViewById(R.id.listViewRiders);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,distanceToRiders);
        listViewRiders.setAdapter(arrayAdapter);

        Log.i("Info","End");

    }

    private List<Location> fillRidersLocations() {
        final List<Location> ridersLocations = new ArrayList<>();
        Log.i("Info","fillRiders");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("Info","fillRiders 1");
                if (e == null) {
                    Log.i("Info","fillRiders 1");
                    for (ParseObject object : objects) {
                        Log.i("Info","fillRiders 3");
                        ParseGeoPoint parseGeoPoint = object.getParseGeoPoint("location");

                        Location ridersLocation = new Location(LocationManager.GPS_PROVIDER);
                        ridersLocation.setLatitude(parseGeoPoint.getLatitude());
                        ridersLocation.setLongitude(parseGeoPoint.getLongitude());

                        Log.i("Info","diiiiiiiiii"+ridersLocation + "km");

                        ridersLocations.add(ridersLocation);
                        Log.i("Info","ridersLocation 1 " + ridersLocations);
                    }
                }
            }
        });
        return ridersLocations;
    }

    private Location lastKnownLocation() {
        Log.i("Info","lastLocation");
        Location lastKnownLocation = new Location(LocationManager.GPS_PROVIDER);
        //if SDK < 23(Marshmallow) - we don't need permission and can ask location immediately
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }//if SDK > 23
        else {
            //check do we have permission, if haven't - ask
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask permission
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }//if have - run code to update location ,take lastKnownLocation to do this
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {

                    return lastKnownLocation;

                }
            }
        }
        return lastKnownLocation;
    }

    private void initializeLocationManager() {
        Log.i("Info","initializeManager");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void initializeLocationListener() {
        Log.i("Info","initializeListener");
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
}
