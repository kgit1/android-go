package com.example.a.appubr;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
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

    ListView requestListView;

    List<String> requests;

    ArrayAdapter arrayAdapter;

    private LocationManager locationManager;

    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        setTitle("Nearby Requests");

        requestListView = (ListView)findViewById(R.id.requestListView);

        requests = new ArrayList<>();

        requests.add("Test");

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,requests);

        requests.clear();

        requests.add("Getting nearby requests");

        requestListView.setAdapter(arrayAdapter);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());

                updateListView(location);
                /*mMap.addMarker(new MarkerOptions().position(userLocation).title(latitude + " : " + longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));*/
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

        //if SDK < 23(Marshmallow) - we don't need permission and can ask location immediately
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }//if SDK > 23
        else {
            //check do we have permission, if haven't - ask
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }//if have - run code to update location ,take lastKnownLocation to do this
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {

                    updateListView(lastKnownLocation);

                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    updateListView(lastKnownLocation);
                }
            }
        }
    }

    public void updateListView(Location location) {

        if (location != null) {
            requests.clear();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");

            final ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

            query.whereNear("location", geoPointLocation);

            query.setLimit(10);

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if(objects.size()>0){

                            for(ParseObject object : objects){

                                Double distanceInMile = geoPointLocation.distanceInMilesTo((ParseGeoPoint) object.getParseGeoPoint("location"));

                                Double distanceInOneDecimal = (double) Math.round(distanceInMile * 10) / 10;

                                requests.add(distanceInOneDecimal+ " miles");

                            }

                            } else{
                            requests.add("No active requests nearby");
                        }

                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            });



        }
        ;

    }

}
